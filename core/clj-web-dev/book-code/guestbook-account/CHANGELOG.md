# Changes

## Base - `guestbook-base/`

Created project from:
`lein new luminus guestbook --template-version 3.91 -- +h2 +http-kit`

Some small changes, but more or less brand new luminus.
`.gitignore` updated to include generated files from later branches and other environment specific files, but include config files for purposes of including in the book.

## Guestbook - `guestbook/`

Write migration for guestbook table

Write markup for messages homepage

Hydrate markup from DB

Write some queries to create messages

Write a POST endpoint for saving messages

write some dummy tests

## Guestbook Validation - `guestbook-validation/`

Add some server-side validation.

## Guestbook Reagent - `guestbook-reagent`

Add reagent dep

Update homepage to use reagent components

Switch from redirect to AJAX

### reagent-pre *offshoot* - `guestbook-reagent-start`

Simplified reagent impl for illustration

## Guestbook Reagent CLJC - `guestbook-cljc`

Move validation logic to CLJC

## Re-Frame Intro - `guestbook-rf-intro`

Refactor reagent into impure re-frame

## Services 1 - `guestbook-services-1`

Separate message logic from endpoints

## Services 2 `guestbook-services`

Move API endpoints into services namespace

## Swagger 1 `guestbook-swagger-1`

Add basic swagger endpoint

## Swagger 2 `guestbook-swagger-2`

Add coercion and other such middleware

## Guestbook Shadow `guestbook-shadow`

Add shadow-cljs compilation

### Shadow Demo `guestbook-shadow-demo`

Demonstrate reloading behaviour of shadow-cljs

## 10x Intro `guestbook-10x`

Add re-frame-10x

## Re Frame 1 `guestbook-re-frame-1`

Re-framify front-end further

## WS Raw 1 `guestbook-websockets`

Add message reloading via websockets

## Sente 1 `guestbook-sente-setup`

Migrate websockets to Sente

## Sente 2 `guestbook-sente-cb`

Use callbacks to handle message status

## Re Frame 2 `guestbook-re-frame-2`

Update websocket send and ajax GET to use effects

## Postgres `guestbook-postgres`

Migrate to postgres

## Postgres 2 `guestbook-postgres-2`

Add auth services and move navbar to client

## login `guestbook-login`

Add auth frontend and logout

## Register `guestbook-register`

Add registration to navbar

## Session `guestbook-session`

Add session fetching

## Author `guestbook-author`

Add author to posts

## Auth `guestbook-authorization`

Add authorization logic for both ws and ajax

## Author 2 `guestbook-author-2`

Add author relationship to posts

## Author 3 `guestbook-author-3`

Add author page stub

## cljc-routing `guestbook-cljc-routing`

Refactor frontend into different namespaces. move routing to CLJC

## Author 4 `guestbook-author-4`

Illustrate race condition arising from dispatch from render

## controllers `guestbook-controllers`

Set up controllers

## profile `guestbook-profile`

Add media and profile, create profile page

## profile-2 `guestbook-profile-2`

Add media uploads

## Account `guestbook-account`

Add account deletion and change password
