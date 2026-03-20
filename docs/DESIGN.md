# Design System Strategy: Precision & Presence



This document outlines the visual and structural logic for the design system. Moving beyond a "standard" utility app, this system is engineered to feel like a high-end tool for professionals—combining the clarity of a technical manual with the sophisticated depth of modern editorial design.



## 1. Overview & Creative North Star: "The Architectural Ledger"



The Creative North Star for this system is **The Architectural Ledger**.



Most workshop and management apps feel cluttered and "boxy." We will break this cycle by utilizing a layout that feels drafted rather than programmed. We achieve this through **intentional asymmetry**, where content is anchored to a strong left-aligned axis, and **tonal layering**, where depth is created by shifting grays rather than drawing lines. The result is a UI that feels open, breathable, and authoritative.



## 2. Color & Surface Logic



We move away from the "flat" web by treating the screen as a physical workspace with varying heights.



### The Color Palette

- **Primary Highlights:** Use `primary` (#003f87) for brand authority and `primary_container` (#0056b3) for interactive vibrancy.

- **The Neutral Core:** A sophisticated range of grays from `surface_container_lowest` (#ffffff) to `surface_dim` (#d9dadb).



### The "No-Line" Rule

**Explicit Instruction:** You are prohibited from using 1px solid borders to section off content.

- Separation must be achieved through background shifts.

- Example: A `surface_container_low` (#f3f4f5) card sitting on a `surface` (#f8f9fa) background creates a natural boundary. Lines create visual noise; tonal shifts create "zones" of focus.



### Surface Hierarchy & Nesting

Treat the UI as a stack of fine paper.

1. **Base Level:** `surface` (#f8f9fa) – The "desk" everything sits on.

2. **Section Level:** `surface_container_low` (#f3f4f5) – Defines large functional areas.

3. **Action Level:** `surface_container_lowest` (#ffffff) – Reserved for cards or inputs that require the user's immediate attention.



### Signature Textures

For high-impact CTAs, do not use flat hex codes. Apply a subtle **Linear Gradient** from `primary` (#003f87) at the top-left to `primary_container` (#0056b3) at the bottom-right. This adds a "machined" luster that feels premium and tactile.



---



## 3. Typography: Editorial Clarity



We use **Inter** to create a typographic hierarchy that feels like a premium publication.



- **Display & Headlines:** Use `headline-md` (1.75rem) for main screen titles. Tighten the letter-spacing (-0.02em) to give it a "locked-in" professional feel.

- **Titles:** Use `title-md` (1.125rem) for card headers. This is the "workhorse" of the app.

- **Body:** Use `body-md` (0.875rem) for descriptions. The reduced size compared to standard web fonts increases the perceived "pro" utility of the interface.

- **Labels:** Use `label-md` (0.75rem) in **All Caps** with +0.05em tracking for category tags or secondary metadata to differentiate them from interactive text.



---



## 4. Elevation & Depth: Tonal Layering



Traditional shadows are too heavy for a "Functional & Professional" look. We use light to define space.



### The Layering Principle

Depth is achieved by "stacking."

- **Level 0:** `surface`

- **Level 1:** `surface_container_low` (used for background grouping)

- **Level 2:** `surface_container_lowest` (used for interactive cards)



### Ambient Shadows

If an element must float (e.g., a Bottom Sheet or FAB), use a **Ghost Shadow**:

- Blur: 24px | Spread: -4px | Opacity: 6% | Color: `on_surface` (#191c1d).

- This creates an "Ambient Occlusion" effect rather than a "Drop Shadow."



### The "Ghost Border" Fallback

If accessibility requirements demand a border (e.g., in high-glare environments), use the `outline_variant` (#c2c6d4) at **15% opacity**. It should be felt, not seen.



---



## 5. Components



### Buttons

- **Primary:** Gradient fill (`primary` to `primary_container`), `on_primary` text, `md` (0.375rem) roundedness.

- **Secondary:** `surface_container_high` fill with `primary` text. No border.

- **Tertiary:** Text-only, using `primary` color, strictly for low-priority actions like "Cancel" or "Learn More."



### Input Fields

- **Container:** `surface_container_highest` (#e1e3e4).

- **Shape:** `sm` (0.125rem) roundedness for a sharper, more technical "workshop" aesthetic.

- **Active State:** Change background to `surface_container_lowest` (#ffffff) and add a 2px `primary` bottom-stroke.



### Cards & Lists

- **Rule:** Forbid divider lines.

- Use `spacing-6` (1.5rem) of vertical white space to separate list items.

- For "Work Orders" or "Referrals," use a `surface_container_lowest` card with a `surface_tint` (#115cb9) vertical accent bar (4px width) on the left edge to denote status.



### Referral Progress Tracker (Specialty Component)

Instead of a standard progress bar, use a series of `surface_container_high` dots. Completed steps glow in `primary`, while the current step uses a `primary_container` pulse. This minimizes visual weight while maximizing clarity.



---



## 6. Do’s and Don’ts



### Do

- **Do** embrace white space. If a screen feels empty, it means the user can breathe.

- **Do** use `title-sm` for labels inside cards to maintain an authoritative tone.

- **Do** align all text to a consistent 16pt (1rem) side padding.



### Don’t

- **Don’t** use pure black (#000000). Use `on_surface` (#191c1d) for all "black" text to maintain tonal softness.

- **Don’t** use icons with varying stroke weights. Use a consistent 1.5px or 2px "Linear" icon set.

- **Don’t** use "Standard" blue. Ensure you are using the specific `primary` (#003f87) to avoid looking like a generic system app.