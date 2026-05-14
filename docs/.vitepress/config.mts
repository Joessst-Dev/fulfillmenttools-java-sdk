import { defineConfig } from 'vitepress'

export default defineConfig({
  title: 'fulfillmenttools Java SDK',
  description: 'Java client library for the fulfillmenttools platform API',
  base: '/fulfillmenttools-java-sdk/',
  themeConfig: {
    search: {
      provider: 'local'
    },
    nav: [
      { text: 'Guide', link: '/getting-started/installation' },
      { text: 'Clients', link: '/clients/carriers' },
      { text: 'GitHub', link: 'https://github.com/Joessst-Dev/fulfillmenttools-java-sdk' }
    ],
    sidebar: [
      {
        text: 'Getting Started',
        items: [
          { text: 'Installation', link: '/getting-started/installation' },
          { text: 'Configuration', link: '/getting-started/configuration' },
          { text: 'Authentication', link: '/getting-started/authentication' }
        ]
      },
      {
        text: 'Resource Clients',
        items: [
          { text: 'Carriers', link: '/clients/carriers' },
          { text: 'Checkout Options', link: '/clients/checkout-options' },
          { text: 'Eventing', link: '/clients/eventing' },
          { text: 'External Actions', link: '/clients/external-actions' },
          { text: 'Facilities', link: '/clients/facilities' },
          { text: 'Facility Connections', link: '/clients/facility-connections' },
          { text: 'Facility Discounts', link: '/clients/facility-discounts' },
          { text: 'Facility Groups', link: '/clients/facility-groups' },
          { text: 'Handover Jobs', link: '/clients/handover-jobs' },
          { text: 'Health', link: '/clients/health' },
          { text: 'Listings', link: '/clients/listings' },
          { text: 'Orders', link: '/clients/orders' },
          { text: 'Pack Jobs', link: '/clients/pack-jobs' },
          { text: 'Pick Jobs', link: '/clients/pick-jobs' },
          { text: 'Processes', link: '/clients/processes' },
          { text: 'Reservations', link: '/clients/reservations' },
          { text: 'Returns', link: '/clients/returns' },
          { text: 'Routing Plans', link: '/clients/routing-plans' },
          { text: 'Routing Strategies', link: '/clients/routing-strategies' },
          { text: 'Sourcing Options', link: '/clients/sourcing-options' },
          { text: 'Stocks', link: '/clients/stocks' },
          { text: 'Storage Locations', link: '/clients/storage-locations' },
          { text: 'Stow Jobs (Inbound)', link: '/clients/inbound' },
          { text: 'Tags', link: '/clients/tags' },
          { text: 'User Management', link: '/clients/users' }
        ]
      },
      {
        text: 'Guides',
        items: [
          { text: 'Error Handling', link: '/guides/error-handling' },
          { text: 'Pagination', link: '/guides/pagination' },
          { text: 'Shared Models', link: '/guides/shared-models' },
          { text: 'Spring Boot', link: '/guides/spring-boot' },
          { text: 'Typed IDs', link: '/guides/typed-ids' }
        ]
      },
      { text: 'Contributing', link: '/contributing' }
    ],
    socialLinks: [
      { icon: 'github', link: 'https://github.com/Joessst-Dev/fulfillmenttools-java-sdk' }
    ]
  }
})
