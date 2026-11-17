---
name: create-mpr-lecture
description: Create or update Polish MPR Java programming lecture materials, including lesson content, diagrams, tables, callouts, bubbles, slides, navigation, and validation. Use when adding a lecture or preparing its materials for teaching.
---

# Creating MPR lecture materials

Use this skill when creating or substantially updating a lecture in this repository. Produce a complete teaching resource consistent with the site's existing structure and visual language, not just an outline.

## Before editing

1. Read the repository README and inspect `site/index.html`, `site/css/style.css`, and `site/js/main.js` for current navigation and shared behavior.
2. Read the closest existing lecture in `site/lectures/`—usually the preceding lecture—and inspect its examples under `examples/` when relevant.
3. Check `git status` first. Preserve unrelated and already-staged user changes.
4. Prefer the smallest coherent change. Do not copy a lecture's inline CSS or JavaScript wholesale without understanding its dependencies and differences.

## Lesson content

- Write student-facing content in Polish, using clear terminology and a practical example tied to the lecture topic.
- Build a teachable progression: opening problem or scenario, learning goals and agenda, concepts, worked examples, common mistakes, practical exercise with a definition of done, and a concise summary.
- Use short Java snippets that illustrate the concept and remain consistent with any executable example. Do not present pseudocode as compilable Java.
- Add tables only when they help compare concepts, decisions, or testing levels. Explain non-obvious entries with contextual bubbles.
- Keep the lesson technically accurate; verify claims against repository examples and tests rather than inventing implementation details.
- Link the lecture to its executable example only if that example exists or is being created as part of the task.

## Visual and interaction conventions

- Reuse the site's dark sci-fi theme and existing shared styles. The lecture palette uses cyan (`#38e8ff`), amber (`#ffb321`), mint (`#6cffb2`), dark surfaces, `Roboto`, and `Space Mono`.
- Use semantic HTML and accessible names for interactive controls. Preserve keyboard navigation and visible focus states.
- For diagrams, prefer inline SVG with a meaningful `viewBox`, descriptive accessible text, and explicit styles for every shape and line class used. Verify fill, stroke, markers, and text color in both the page and the slide clone; a class without a matching rule can make all SVG geometry appear to vanish.
- Use a styled `.domain-note` segment for longer context, reading instructions, or a takeaway that should remain visible without interaction. Keep it with the relevant content when splitting slides, and do not hide it in slide mode.
- Use `<details class="comment-help">` with a `.comment-bubble` inside a `.signal-cell` for short explanations attached to table rows. Give each summary a useful accessible name and keep only one bubble open at a time.
- Use `.lecture-note` bubbles for optional contextual notes near content. Keep longer explanatory prose in a visible segment rather than turning every note into a bubble.
- For code and diagrams, preserve the lecture's copy and zoom controls. Slide controls may need to be attached after a slide is cloned; test both direct slide controls and the expanded modal.
- Treat CSS selectors, SVG classes, markup, slide cloning, and event-handler order as one feature. Keep selectors consistent and avoid duplicate or conflicting declarations.

## Slides and navigation

- Include a slide-launch control and keep slide content grouped into readable, focused cards.
- Give a complex diagram its own slide when needed. Fit slides to the available stage without cropping content, and keep annotations, callout segments, and bubbles usable on the clone.
- Support the existing slide controls and keyboard navigation. Make sure Escape closes the active modal or deck without leaving the page locked.
- Add previous/next lecture links and update `site/index.html` when adding a new lecture. Keep numbering and filenames consistent with neighboring lessons.

## Examples and documentation

- If the task includes an executable exercise, follow the existing `examples/` structure and Maven conventions. Include a student-facing README and focused tests where appropriate.
- Update only documentation directly related to the lecture or example. Do not add planning or tracking files unless requested.

## Validation checklist

1. Run `git diff --check` and inspect the final diff, including staged changes; do not stage or commit unless requested.
2. Parse every inline script or run the repository's relevant checks. Avoid relying on a superficial tag-count check for HTML correctness.
3. Open or reload the lecture in a browser and inspect both the regular page and slide mode at desktop and narrow widths.
4. Exercise slide launch, next/previous, keyboard navigation, Escape, copy, zoom, and close controls.
5. Click each kind of explanation: table bubbles, optional note bubbles, and visible context segments. Confirm bubbles are not clipped and segments are not accidentally hidden or separated from their diagram.
6. Inspect every SVG diagram at actual rendered size. Confirm its shapes, arrows, labels, and annotation controls remain visible in both the page and slide mode.
7. If an executable example changed, run its focused Maven tests and report the exact result.
