(() => {
    const copyText = async (text, button) => {
        const label = button.textContent;
        try {
            await navigator.clipboard.writeText(text);
            button.textContent = 'Skopiowano ✓';
        } catch (error) {
            button.textContent = 'Błąd';
        }
        setTimeout(() => button.textContent = label, 1500);
    };

    const overlay = document.createElement('div');
    overlay.className = 'presentation-overlay';
    overlay.setAttribute('role', 'dialog');
    overlay.setAttribute('aria-modal', 'true');
    overlay.innerHTML = `
        <div class="presentation-toolbar">
            <span class="presentation-title"></span>
            <button data-action="copy" hidden>Kopiuj</button>
            <button data-action="minus">−</button>
            <button data-action="reset">100%</button>
            <button data-action="plus">+</button>
            <button data-action="close">Zamknij ×</button>
        </div>
        <div class="presentation-content"><div class="presentation-scale"></div></div>`;
    document.body.append(overlay);

    const modalScale = overlay.querySelector('.presentation-scale');
    const modalTitle = overlay.querySelector('.presentation-title');
    const modalCopy = overlay.querySelector('[data-action="copy"]');
    const modalReset = overlay.querySelector('[data-action="reset"]');
    let modalZoom = 1;
    let currentCode = '';
    let lastFocused = null;

    const applyModalZoom = () => {
        modalScale.style.zoom = modalZoom;
        modalReset.textContent = Math.round(modalZoom * 100) + '%';
    };

    const closeDetail = () => {
        overlay.classList.remove('is-open');
        document.body.classList.remove('presentation-active');
        modalScale.replaceChildren();
        lastFocused?.focus();
    };

    const openDetail = (target, heading, trigger) => {
        lastFocused = trigger;
        modalZoom = 1;
        currentCode = target.matches('pre') ? target.innerText : '';
        modalTitle.textContent = `${target.matches('.diagram') ? 'Diagram' : 'Kod'} // ${heading}`;
        modalCopy.hidden = !currentCode;
        const clone = target.cloneNode(true);
        clone.querySelectorAll('.slide-asset-controls,.expand-control,.copy-control').forEach(control => control.remove());
        modalScale.replaceChildren(clone);
        applyModalZoom();
        overlay.classList.add('is-open');
        document.body.classList.add('presentation-active');
        overlay.querySelector('[data-action="close"]').focus();
    };

    overlay.addEventListener('click', event => {
        const action = event.target.closest('button')?.dataset.action;
        if (action === 'close') closeDetail();
        if (action === 'copy') copyText(currentCode, modalCopy);
        if (action === 'plus') {
            modalZoom = Math.min(2, modalZoom + .15);
            applyModalZoom();
        }
        if (action === 'minus') {
            modalZoom = Math.max(.5, modalZoom - .15);
            applyModalZoom();
        }
        if (action === 'reset') {
            modalZoom = 1;
            applyModalZoom();
        }
    });

    document.addEventListener('keydown', event => {
        if (event.key === 'Escape' && overlay.classList.contains('is-open')) {
            event.stopImmediatePropagation();
            closeDetail();
        }
    }, true);

    document.querySelectorAll('main .diagram, main pre').forEach(target => {
        const heading = target.closest('section')?.querySelector('h2,h3')?.textContent || 'Materiał';
        let host = target;
        if (target.matches('pre')) {
            const frame = document.createElement('div');
            frame.className = 'focus-frame';
            target.before(frame);
            frame.append(target);
            host = frame;
            const copy = document.createElement('button');
            copy.className = 'copy-control';
            copy.textContent = 'Kopiuj';
            copy.addEventListener('click', () => copyText(target.innerText, copy));
            host.prepend(copy);
        }
        const expand = document.createElement('button');
        expand.className = 'expand-control';
        expand.textContent = '⛶ Powiększ';
        expand.addEventListener('click', () => openDetail(target, heading, expand));
        host.prepend(expand);
    });

    const launch = document.getElementById('slide-launch');
    const deck = document.createElement('div');
    deck.className = 'slide-deck';
    deck.setAttribute('role', 'dialog');
    deck.setAttribute('aria-modal', 'true');
    deck.innerHTML = `
        <div class="slide-deck-bar">
            <span class="slide-deck-brand">MPR // BOUNDED CONTEXT</span>
            <button data-slide="first">|◀</button>
            <button data-slide="prev">◀</button>
            <button data-slide="next">▶</button>
            <button data-slide="close">Zamknij ×</button>
        </div>
        <div class="slide-stage"><div class="slide-scale"></div></div>
        <div class="slide-deck-footer">
            <div class="slide-progress"><span></span></div>
            <span class="slide-counter"></span>
        </div>`;
    document.body.append(deck);

    const stage = deck.querySelector('.slide-stage');
    const slideScale = deck.querySelector('.slide-scale');
    const counter = deck.querySelector('.slide-counter');
    const progress = deck.querySelector('.slide-progress span');
    let slides = [];
    let slideIndex = 0;

    const weight = element => {
        if (element.matches('.diagram,.dialogue,.cards')) return 5;
        if (element.matches('table,pre,.focus-frame')) return 4;
        if (element.matches('ol,ul')) return 3;
        if (element.matches('.question')) return 2;
        return 1;
    };

    const makeSlide = (title, nodes, part) => {
        const card = document.createElement('section');
        card.className = 'slide-card';
        if (part > 1) {
            const label = document.createElement('div');
            label.className = 'slide-part';
            label.textContent = `${title.textContent} // część ${part}`;
            card.append(label);
        }
        card.append(title.cloneNode(true));
        nodes.forEach(node => card.append(node.cloneNode(true)));
        card.querySelectorAll('.expand-control,.copy-control,.slide-asset-controls').forEach(control => control.remove());
        return card;
    };

    const buildSlides = () => {
        slides = [];
        document.querySelectorAll('main > section').forEach(section => {
            if (section.hasAttribute('style')) return;
            const title = section.querySelector(':scope > h1,:scope > h2');
            if (!title) return;
            const children = [...section.children].filter(child => child !== title);
            if (section.id === 'top') {
                slides.push(makeSlide(title, children, 1));
                return;
            }
            let group = [];
            let score = 0;
            let part = 1;
            const flush = () => {
                if (!group.length) return;
                slides.push(makeSlide(title, group, part++));
                group = [];
                score = 0;
            };
            for (let index = 0; index < children.length; index++) {
                const child = children[index];
                const childWeight = weight(child);
                if (score + childWeight > 7 && group.length) flush();
                group.push(child);
                score += childWeight;
                if (child.matches('.diagram') && children[index + 1]?.matches('.lecture-note')) {
                    group.push(children[++index]);
                    score += 1;
                    flush();
                } else if (child.matches('.dialogue,.cards')) {
                    flush();
                }
            }
            flush();
        });
    };

    const fitSlide = () => {
        slideScale.style.transform = 'translate(-50%,-50%)';
        const availableWidth = Math.max(1, stage.clientWidth - 96);
        const availableHeight = Math.max(1, stage.clientHeight - 72);
        const factor = Math.min(1, availableWidth / slideScale.scrollWidth, availableHeight / slideScale.scrollHeight);
        slideScale.style.transform = `translate(-50%,-50%) scale(${factor})`;
    };

    const enhanceSlide = card => {
        const notes = [...card.querySelectorAll('details.lecture-note')];
        notes.forEach(note => {
            note.open = false;
            note.addEventListener('toggle', () => {
                if (!note.open) return;
                notes.forEach(other => {
                    if (other !== note) other.open = false;
                });
            });
        });

        card.querySelectorAll('.diagram,pre').forEach(target => {
            const host = target.matches('pre') ? target.parentElement : target;
            host.classList.add('slide-asset');
            const controls = document.createElement('div');
            controls.className = 'slide-asset-controls';

            if (target.matches('pre')) {
                const copy = document.createElement('button');
                copy.type = 'button';
                copy.className = 'slide-copy-control';
                copy.textContent = 'Kopiuj';
                copy.addEventListener('click', () => copyText(target.innerText, copy));
                controls.append(copy);
            }

            const expand = document.createElement('button');
            expand.type = 'button';
            expand.className = 'slide-detail-control';
            expand.textContent = '⛶ Powiększ';
            const heading = card.querySelector('h1,h2,h3')?.textContent || 'Materiał';
            expand.addEventListener('click', () => openDetail(target, heading, expand));
            controls.append(expand);
            host.prepend(controls);
        });
    };

    const renderSlide = () => {
        slideIndex = Math.max(0, Math.min(slideIndex, slides.length - 1));
        const current = slides[slideIndex].cloneNode(true);
        enhanceSlide(current);
        slideScale.replaceChildren(current);
        counter.textContent = `${slideIndex + 1} / ${slides.length}`;
        progress.style.width = `${(slideIndex + 1) / slides.length * 100}%`;
        requestAnimationFrame(fitSlide);
    };

    const closeDeck = () => {
        deck.classList.remove('is-open');
        document.body.classList.remove('slide-active');
        launch.focus();
    };

    launch.addEventListener('click', () => {
        buildSlides();
        slideIndex = 0;
        deck.classList.add('is-open');
        document.body.classList.add('slide-active');
        renderSlide();
        deck.querySelector('[data-slide="next"]').focus();
    });

    deck.addEventListener('click', event => {
        const action = event.target.closest('button')?.dataset.slide;
        if (action === 'close') closeDeck();
        if (action === 'next') {
            slideIndex++;
            renderSlide();
        }
        if (action === 'prev') {
            slideIndex--;
            renderSlide();
        }
        if (action === 'first') {
            slideIndex = 0;
            renderSlide();
        }
    });

    document.addEventListener('keydown', event => {
        if (!deck.classList.contains('is-open') || overlay.classList.contains('is-open')) return;
        if (event.key === 'Escape') closeDeck();
        if (['ArrowRight', 'PageDown', ' '].includes(event.key)) {
            event.preventDefault();
            slideIndex++;
            renderSlide();
        }
        if (['ArrowLeft', 'PageUp'].includes(event.key)) {
            event.preventDefault();
            slideIndex--;
            renderSlide();
        }
        if (event.key === 'Home') {
            slideIndex = 0;
            renderSlide();
        }
        if (event.key === 'End') {
            slideIndex = slides.length - 1;
            renderSlide();
        }
    });

    window.addEventListener('resize', () => {
        if (deck.classList.contains('is-open')) fitSlide();
    });

    const pageNotes = [...document.querySelectorAll('main details.lecture-note')];
    pageNotes.forEach(note => note.addEventListener('toggle', () => {
        if (note.open) pageNotes.forEach(other => {
            if (other !== note) other.open = false;
        });
    }));
})();
