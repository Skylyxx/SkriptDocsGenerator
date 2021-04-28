export default {
  // Disable server-side rendering: https://go.nuxtjs.dev/ssr-mode
  ssr: false,

  // Target: https://go.nuxtjs.dev/config-target
  target: 'static',

  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: 'SkriptDocsGenerator',
    htmlAttrs: {
      lang: 'en'
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      
      { property: 'og:title', content: 'SkriptDocsGenerator' },
      { property: 'og:description', content: 'A simple plugin to generate automatically documentation for yours Skript addons' },
      { property: 'og:url', content: 'https://docsgenerator.skylyxx.club/' },
      { property: 'og:image', content: 'https://docsgenerator.skylyxx.club/favicon.ico' },
      { property: 'og:theme-color', content: '#D6DE7A' },
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [
  ],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
    '~/plugins/markdown'
  ],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
    '@nuxtjs/tailwindcss'
  ],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [],
  markdownit: {
    html: true,
    linkify: true,
    typographer: true
  },
  markdownit: {
    runtime: true // Support `$md()`
  },

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
  },
  generate: {
    routes: ['404']
  }
}
