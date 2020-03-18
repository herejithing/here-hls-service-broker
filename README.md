
# Service Broker for HERE Location Services

Service Broker for HERE Location Services is an implementation of the [Open Service Broker][osb] for  [HERE][here] enterprise-grade, SLA-backed HERE Location Services. It  enables discovery and provisioning of HERE Location Services REST APIs in a cloud cluster.

## HERE Location Services
### Description
[HERE Location Services][here-location-services] are key in use cases such as Fleet Utilization, Supply Chain Optimization, Urban Mobility, etc. They open up new location intelligence opportunities in diverse enterprise and technology areas, including the Internet of Things, Automotive, Insurance, Advertising, Mobile Payments, Public Sector, Smart Cities, Telecom, Utilities, and Transportation and Logistics. HERE Location Services cover Geocoder, Search, Navigation, Routing, Fleet Telematics, Positioning and other services, providing soultions for map visualization, navigation, routing, geocoding, time zone lookups, geofencing, custom locations, routing, route matching GPS traces, geospatial, sequencing multiple waypoints, truck routing, positioning, etc.

### Features and Capabilities
| Feature  | Description |
| :------------- | :------------- |
| Map Tile API  | Integrate server-rendered raster 2D map tiles in multiple styles, such as base, aerial and fleet. Use different zoom levels, display options, map views and schemes, including the Truck Attributes Map layer for large vehicles. |
| Map Image API | Get access to pre-rendered map images that are optimized for desktop and mobile devices. Add route polygons, POI labels and other objects on top of the map to give your users the insights they need. |
|Vector Tile API | Integrate client-side rendered vector tiles and customize the look and feel of the map by changing, removing or adding specific map properties or objects. |
| Geocoding and Search API | Accurately pinpoint geo-coordinates, addresses and Places/POIs on the map; explore a vast database of Places/POIs through one box search and get better suggestions with fewer strokes with autosuggest. |
| Routing API | Complete complex journeys more efficiently with advanced routing algorithms including truck routing, large scale matrix routing and traffic-enabled routing. Get accurate ETAs and routing instructions in over 108 languages. |
| Public Transit API| Provide the best public transit routes while highlighting walking directions to stops, pedestrian access points, station locations and transfer locations along the way. |
| Intermodal Routing API | Provide a choice of routes that combine car, bike, taxi, pedestrian and public transit modes while taking into account several variables: real-time traffic, incidents, public transit timetables and other dynamic information. |
| Traffic API | Add real-world context to your application by integrating real-time and historical traffic information about accidents, congestion, construction and more. |
| Fleet Telematics API | Integrate advanced algorithms ready to solve complex location problems, such as toll cost calculation, route matching GPS traces and geofencing. |
| Weather API | Get current weather reports or weather forecasts and check for severe weather alerts at a specific location. |
| Positioning API | Build applications that require location estimates based on radio network measurement data. Supported measurement data includes 2G, 3G, 4G, and WLAN measurements. |

# Getting Started
[Get started for FREE][here-dev-sign-up-osb] with HERE Location Services to add location intelligence to any applications and solutions in your cluster. 

The following guides make it easy for you to install the Service Broker. To proceed, choose the target platform for the installation:
  * [OpenShift](/docs/openshift.md)
  * [Kubernetes](/docs/kubernetes.md)
  
For HERE Location Services REST API Documentation please go to: [Documentation](/docs/HLSRESTAppID.md)
  

# License

Copyright (C) 2019 HERE Europe B.V.

See the [LICENSE](./LICENSE) file in the root of this project for license details.

[here]: https://www.here.com/

[here-dev-plans]: https://developer.here.com/plans

[here-dev-sign-up-osb]: https://developer.here.com/plans?utm_medium=referral&utm_source=GitHub-Service-Broker&create=Freemium

[here-location-services]: https://www.here.com/products/location-based-services

[osb]: https://www.openservicebrokerapi.org

[osb-spec]: https://github.com/openservicebrokerapi/servicebroker/blob/v2.14/spec.md

[youtube-here-osb]: https://www.youtube.com/user/heremaps
