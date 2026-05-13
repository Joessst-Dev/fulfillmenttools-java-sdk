import { defineConfig } from 'vitepress'

export default defineConfig({
  title: 'fulfillmenttools Java SDK',
  description: 'Java client library for the fulfillmenttools platform API',
  base: '/fulfillmenttools-java-sdk/',
  themeConfig: {
    nav: [
      { text: 'Guide', link: '/getting-started/installation' },
      { text: 'Clients', link: '/clients/orders' },
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
          { text: 'Orders', link: '/clients/orders' },
          { text: 'Facilities', link: '/clients/facilities' },
          { text: 'Stocks', link: '/clients/stocks' },
          { text: 'Pick Jobs', link: '/clients/pick-jobs' },
          { text: 'Pack Jobs', link: '/clients/pack-jobs' },
          { text: 'Handover Jobs', link: '/clients/handover-jobs' },
          { text: 'Returns', link: '/clients/returns' },
          { text: 'Inbound (Stow Jobs)', link: '/clients/inbound' },
          { text: 'Reservations', link: '/clients/reservations' },
          { text: 'Carriers', link: '/clients/carriers' },
          { text: 'Storage Locations', link: '/clients/storage-locations' },
          { text: 'Listings', link: '/clients/listings' },
          { text: 'Tags', link: '/clients/tags' },
          { text: 'Routing Plans', link: '/clients/routing-plans' },
          { text: 'Routing Strategies', link: '/clients/routing-strategies' },
          { text: 'Sourcing Options', link: '/clients/sourcing-options' },
          { text: 'Checkout Options', link: '/clients/checkout-options' },
          { text: 'Processes', link: '/clients/processes' },
          { text: 'User Management', link: '/clients/users' },
          { text: 'Eventing', link: '/clients/eventing' },
          { text: 'External Actions', link: '/clients/external-actions' },
          { text: 'Facility Groups', link: '/clients/facility-groups' },
          { text: 'Facility Discounts', link: '/clients/facility-discounts' },
          { text: 'Facility Connections', link: '/clients/facility-connections' },
          { text: 'Health', link: '/clients/health' }
        ]
      },
      {
        text: 'Guides',
        items: [
          { text: 'Error Handling', link: '/guides/error-handling' },
          { text: 'Pagination', link: '/guides/pagination' },
          { text: 'Spring Boot', link: '/guides/spring-boot' }
        ]
      },
      { text: 'Contributing', link: '/contributing' }
    ],
    socialLinks: [
      { icon: 'github', link: 'https://github.com/Joessst-Dev/fulfillmenttools-java-sdk' }
    ]
  }
})
