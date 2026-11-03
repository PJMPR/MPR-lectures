// MPR Lectures - Main JavaScript

document.addEventListener('DOMContentLoaded', function() {
  // Smooth scroll for nav links
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        target.scrollIntoView({
          behavior: 'smooth',
          block: 'start'
        });
      }
    });
  });

  // Add hover effect to lecture cards
  document.querySelectorAll('.lecture-card').forEach(card => {
    card.addEventListener('mouseenter', function() {
      this.style.animation = 'none';
      setTimeout(() => {
        this.style.animation = '';
      }, 10);
    });
  });

  // Add scroll indicator
  let scrollIndicator = document.createElement('div');
  scrollIndicator.className = 'scroll-indicator';
  scrollIndicator.style.cssText = `
    position: fixed;
    top: 0;
    left: 0;
    height: 3px;
    background: linear-gradient(90deg, #38e8ff, #ffb321, #ff4b35);
    width: 0%;
    z-index: 101;
    transition: width 0.2s ease;
  `;
  document.body.appendChild(scrollIndicator);

  window.addEventListener('scroll', function() {
    let scrollTop = window.scrollY;
    let docHeight = document.documentElement.scrollHeight - window.innerHeight;
    let scrollPercent = (scrollTop / docHeight) * 100;
    scrollIndicator.style.width = scrollPercent + '%';
  });

  // Highlight current nav section on scroll
  window.addEventListener('scroll', function() {
    let current = '';
    const scrollTop = window.scrollY;
    const sections = document.querySelectorAll('section');
    
    sections.forEach(section => {
      const sectionTop = section.offsetTop;
      if (scrollTop >= sectionTop - 200) {
        current = section.getAttribute('id');
      }
    });

    document.querySelectorAll('.nav-links a').forEach(link => {
      link.style.borderColor = 'transparent';
      if (link.getAttribute('href') === '#' + current) {
        link.style.borderColor = '#38e8ff';
      }
    });
  });

  // Lecture 05: present every dialogue as consecutive pairs of statements.
  if (document.title.startsWith('DDD: taktyczne building blocks')) {
    const dialogues = [...document.querySelectorAll('main .dialogue')];

    if (dialogues.length) {
      const styles = document.createElement('style');
      styles.textContent = `
        .dialogue-present-control { display:flex; justify-content:flex-end; margin:-.75rem 0 1.5rem; }
        .dialogue-present-button { padding:.62rem .9rem; color:#041014; background:#38e8ff; border:1px solid #9af4ff; font:700 .68rem/1 'Space Mono',monospace; letter-spacing:.05em; text-transform:uppercase; cursor:pointer; }
        .dialogue-present-button:hover, .dialogue-present-button:focus-visible { background:#a9f7ff; outline:2px solid #fff; outline-offset:2px; }
        .dialogue-modal { position:fixed; inset:0; z-index:2100; display:none; align-items:center; justify-content:center; padding:clamp(1rem,4vw,3rem); background:rgba(2,6,8,.96); backdrop-filter:blur(12px); }
        .dialogue-modal.is-open { display:flex; }
        .dialogue-modal-panel { display:flex; flex-direction:column; width:min(1100px,100%); max-height:100%; background:#081116; border:1px solid #38e8ff; box-shadow:0 0 60px rgba(56,232,255,.18); }
        .dialogue-modal-header, .dialogue-modal-footer { display:flex; align-items:center; gap:.75rem; padding:.85rem 1rem; background:#0b171c; }
        .dialogue-modal-header { border-bottom:1px solid #263b42; }
        .dialogue-modal-title { margin-right:auto; color:#d6e5e7; font:700 .74rem/1.35 'Space Mono',monospace; letter-spacing:.07em; text-transform:uppercase; }
        .dialogue-modal-close, .dialogue-modal-nav { padding:.62rem .85rem; color:#d6e5e7; background:#12242b; border:1px solid #38515a; font:700 .7rem 'Space Mono',monospace; cursor:pointer; }
        .dialogue-modal-close { color:#fff; border-color:#ff4b35; background:rgba(255,75,53,.14); }
        .dialogue-modal-close:hover, .dialogue-modal-close:focus-visible, .dialogue-modal-nav:hover, .dialogue-modal-nav:focus-visible { color:#041014; background:#38e8ff; outline:2px solid #fff; outline-offset:2px; }
        .dialogue-modal-nav:disabled { opacity:.35; cursor:not-allowed; }
        .dialogue-modal-stage { display:grid; grid-template-columns:1fr 1fr; gap:1.25rem; min-height:0; padding:clamp(1rem,4vw,3rem); overflow:auto; }
        .dialogue-modal-stage .speech { display:flex; flex-direction:column; justify-content:center; min-height:clamp(180px,35vh,380px); font-size:clamp(1rem,2vw,1.45rem); line-height:1.55; }
        .dialogue-summary-slide { grid-column:1 / -1; display:flex; flex-direction:column; justify-content:center; min-height:clamp(260px,45vh,480px); }
        .dialogue-summary-slide h3 { margin:0 0 1.5rem; color:#ffb321; font:700 clamp(1rem,2vw,1.35rem) 'Space Mono',monospace; letter-spacing:.06em; text-transform:uppercase; }
        .dialogue-summary-slide .question, .dialogue-summary-slide > p { margin:0; padding:clamp(1.25rem,3vw,2rem); border:1px solid #ffb321; border-left:5px solid #ffb321; background:rgba(255,179,33,.08); font-size:clamp(1rem,2vw,1.4rem); line-height:1.6; }
        .dialogue-modal-footer { justify-content:center; border-top:1px solid #263b42; }
        .dialogue-modal-counter { min-width:8rem; text-align:center; color:#9fb2b6; font:700 .7rem 'Space Mono',monospace; }
        .dialogue-modal-hint { margin-right:auto; color:#82969a; font:.65rem 'Space Mono',monospace; }
        @media (max-width:760px) {
          .dialogue-modal { padding:.5rem; }
          .dialogue-modal-stage { grid-template-columns:1fr; gap:.75rem; }
          .dialogue-modal-stage .speech { min-height:auto; }
          .dialogue-modal-hint { display:none; }
          .dialogue-modal-footer { justify-content:space-between; }
          .dialogue-modal-counter { min-width:auto; }
          .dialogue-modal-title { font-size:.62rem; }
        }
      `;
      document.head.appendChild(styles);

      let pairs = [];
      let pairIndex = 0;
      let lastTrigger = null;
      const modal = document.createElement('div');
      modal.className = 'dialogue-modal';
      modal.setAttribute('role', 'dialog');
      modal.setAttribute('aria-modal', 'true');
      modal.setAttribute('aria-hidden', 'true');
      modal.setAttribute('aria-labelledby', 'dialogue-modal-title');
      modal.innerHTML = `
        <div class="dialogue-modal-panel">
          <div class="dialogue-modal-header">
            <span class="dialogue-modal-title" id="dialogue-modal-title"></span>
            <button type="button" class="dialogue-modal-close" data-dialogue-action="close">Zamknij ×</button>
          </div>
          <div class="dialogue-modal-stage" aria-live="polite"></div>
          <div class="dialogue-modal-footer">
            <span class="dialogue-modal-hint">← → przechodzenie · Esc zamykanie</span>
            <button type="button" class="dialogue-modal-nav" data-dialogue-action="prev">← Poprzednia</button>
            <span class="dialogue-modal-counter"></span>
            <button type="button" class="dialogue-modal-nav" data-dialogue-action="next">Następna →</button>
          </div>
        </div>`;
      document.body.appendChild(modal);

      const stage = modal.querySelector('.dialogue-modal-stage');
      const title = modal.querySelector('.dialogue-modal-title');
      const counter = modal.querySelector('.dialogue-modal-counter');
      const prev = modal.querySelector('[data-dialogue-action="prev"]');
      const next = modal.querySelector('[data-dialogue-action="next"]');

      const findConversationSummary = dialogue => {
        let candidate = null;
        let element = dialogue.nextElementSibling;

        while (element && !element.matches('.dialogue')) {
          if (element.querySelector('.dialogue')) break;
          const strongText = element.querySelector('strong')?.textContent?.trim() || '';
          if (element.matches('.question') ||
              (element.matches('p') && /^(wniosek|rezultat|podsumowanie)/i.test(strongText))) {
            candidate = element;
            break;
          }
          element = element.nextElementSibling;
        }

        return candidate;
      };

      const renderPair = () => {
        stage.replaceChildren(...pairs[pairIndex].map(speech => speech.cloneNode(true)));
        counter.textContent = pairIndex === pairs.length - 1 && stage.querySelector('.dialogue-summary-slide')
          ? `Podsumowanie · ${pairIndex + 1} / ${pairs.length}`
          : `${pairIndex + 1} / ${pairs.length}`;
        prev.disabled = pairIndex === 0;
        next.disabled = pairIndex === pairs.length - 1;
        stage.scrollTop = 0;
      };

      const closeDialogue = () => {
        modal.classList.remove('is-open');
        modal.setAttribute('aria-hidden', 'true');
        document.body.classList.remove('presentation-active');
        stage.replaceChildren();
        lastTrigger?.focus();
      };

      const openDialogue = (dialogue, trigger, index) => {
        lastTrigger = trigger;
        pairIndex = 0;
        const speeches = [...dialogue.querySelectorAll(':scope > .speech')];
        pairs = [];
        for (let i = 0; i < speeches.length; i += 2) pairs.push(speeches.slice(i, i + 2));

        const summary = findConversationSummary(dialogue);
        if (summary) {
          const summarySlide = document.createElement('div');
          summarySlide.className = 'dialogue-summary-slide';
          const summaryTitle = document.createElement('h3');
          summaryTitle.textContent = 'Podsumowanie rozmowy';
          summarySlide.append(summaryTitle, summary.cloneNode(true));
          pairs.push([summarySlide]);
        }

        const section = dialogue.closest('section');
        const headingsBeforeDialogue = [...section.querySelectorAll('h2,h3')]
          .filter(heading => heading.compareDocumentPosition(dialogue) & Node.DOCUMENT_POSITION_FOLLOWING);
        title.textContent = `Dialog // ${headingsBeforeDialogue.pop()?.textContent?.trim() || `Rozmowa ${index + 1}`}`;
        renderPair();
        modal.classList.add('is-open');
        modal.setAttribute('aria-hidden', 'false');
        document.body.classList.add('presentation-active');
        next.focus();
      };

      dialogues.forEach((dialogue, index) => {
        const controls = document.createElement('div');
        controls.className = 'dialogue-present-control';
        const button = document.createElement('button');
        button.type = 'button';
        button.className = 'dialogue-present-button';
        button.textContent = '▶ Prezentuj dialog';
        button.setAttribute('aria-label', `Prezentuj dialog ${index + 1} wypowiedź po wypowiedzi`);
        button.addEventListener('click', () => openDialogue(dialogue, button, index));
        controls.appendChild(button);
        dialogue.insertAdjacentElement('afterend', controls);
      });

      modal.addEventListener('click', event => {
        const action = event.target.closest('button')?.dataset.dialogueAction;
        if (action === 'close') closeDialogue();
        if (action === 'prev' && pairIndex > 0) { pairIndex--; renderPair(); }
        if (action === 'next' && pairIndex < pairs.length - 1) { pairIndex++; renderPair(); }
      });

      document.addEventListener('keydown', event => {
        if (!modal.classList.contains('is-open')) return;
        if (event.key === 'Escape') closeDialogue();
        if (event.key === 'ArrowLeft' && pairIndex > 0) { pairIndex--; renderPair(); }
        if ((event.key === 'ArrowRight' || event.key === ' ') && pairIndex < pairs.length - 1) {
          event.preventDefault();
          pairIndex++;
          renderPair();
        }
      });
    }
  }

  console.log('MPR Lectures - Ready! 🚀');
});
