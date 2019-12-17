
# Service Broker for HERE Location Services

Service Broker for HERE Location Services is an implementation of the [Open Service Broker][osb] for  [HERE][here] enterprise-grade, SLA-backed HERE Location Services. It  enables discovery and provisioning of [HERE Location Services REST APIs][here-dev-rest] in a cloud cluster.

## HERE Location Services
### Description
[HERE Location Services][here-location-services] are key in use cases such as Fleet Utilization, Supply Chain Optimization, Urban Mobility, etc. They open up new location intelligence opportunities in diverse enterprise and technology areas, including the Internet of Things, Automotive, Insurance, Advertising, Mobile Payments, Public Sector, Smart Cities, Telecom, Utilities, and Transportation and Logistics. HERE Location Services cover Geocoder, Search, Navigation, Routing, Fleet Telematics, Positioning and other services, providing soultions for map visualization, navigation, routing, geocoding, time zone lookups, geofencing, custom locations, routing, route matching GPS traces, geospatial, sequencing multiple waypoints, truck routing, positioning, etc.

### Features and Capabilities
| Feature  | Description |
| :------------- | :------------- |
| [Map Tile API][here-dev-rest-maptile]  | Integrate server-rendered raster 2D map tiles in multiple styles, such as base, aerial and fleet. Use different zoom levels, display options, map views and schemes, including the Truck Attributes Map layer for large vehicles.  |
| [Map Tile API][here-dev-rest-maptile]  | Get access to pre-rendered map images optimized for desktop and mobile devices. Add route polygons, POI labels and other objects on top of the map to give your users the insights they need.  |
| [Geovisualization API][here-dev-rest-geovisual]  | Develop sophisticated visualizations of geographically related data through heat maps, choropleth maps and more.  |
| [Geocoder API][here-dev-rest-geocoder] | Convert and accurately match addresses to geocoordinates and vice versa with over 360 million hyper-precise point addresses in 108 countries. Upload up to 100 pairs of coordinates and process them in a single synchronous request. |
| [Geocoder Autocomplete API][here-dev-rest-geocoder_ac] | Correct misspellings and offer better suggestions for address searches, all with fewer keystrokes. Apply filters for a specific country or list of countries and provide faster and more accurate address matching. |
| [Batch Geocoder API][here-dev-rest-batch_geocoder] | Improve response time per coordinate by uploading up to a million forward or reverse geocoder requests, or up to 2GB records per request file. |
| [Places API][here-dev-rest-places] | Get global information on 150 million parks, businesses, attractions and more, including addresses, categories and contact info. |
| [Routing API][here-dev-rest-routing] | Complete complex journeys more efficiently with advanced routing algorithms including truck routing, large scale matrix routing and traffic-enabled routing. Get accurate ETAs and routing instructions in over 108 languages. |
| [Public Transit API][here-dev-rest-transit] | Provide the best public transit routes while highlighting walking directions to stops, pedestrian access points, station locations and transfer locations along the way. |
| [Intermodal Routing API][here-dev-rest-im_routing] | Provide a choice of routes that combine drive, park, ride and walk functions while taking into account several variables: real-time traffic, incidents, public transit timetables and other dynamic information. |
| [Traffic API][here-dev-rest-traffic] | Add real-world context to your application by integrating real-time and historical traffic information about accidents, congestion, construction and more. |
| [Fleet Telematics API][here-dev-rest-fleet] | Integrate advanced algorithms ready to solve complex location problems, such as toll cost calculation, route matching GPS traces and geofencing. |
| [Weather API][here-dev-rest-weather] | Get current weather reports or weather forecasts and check for severe weather alerts at a specific location. |
| [Tracking API][here-dev-rest-tracking] | Store and retrieve the location and user-defined telemetry of IoT devices for real-time and historical tracking and geofencing. |
| [Positioning API][here-dev-rest-positioning] | Build applications that require location estimates based on radio network measurement data. Supported measurement data includes 2G, 3G, 4G, and WLAN measurements. |

# Getting Started
[Get started for FREE][here-dev-sign-up-osb] with HERE Location Services to add location intelligence to any applications and solutions in your cluster. 

The following guides make it easy for you to install the Service Broker. To proceed, choose the target platform for the installation:
  * [OpenShift](/docs/openshift.md)
  * [Kubernetes](/docs/kubernetes.md)
  

# License

Copyright (C) 2019 HERE Europe B.V.

See the [LICENSE](./LICENSE) file in the root of this project for license details.

[here]: https://www.here.com/

[here-dev-rest]: https://developer.here.com/rest_api

[here-dev-rest-maptile]: https://developer.here.com/documentation/map-tile/topics/quick-start-map-tile.html

[here-dev-rest-mapimage]: https://developer.here.com/documentation/map-image/topics/quick-start-show-default-location.html

[here-dev-rest-geovisual]: https://developer.here.com/documentation/geovisualization/topics/overview.html

[here-dev-rest-geocoder]: https://developer.here.com/documentation/geocoder/topics/quick-start-geocode.html

[here-dev-rest-geocoder_ac]: https://developer.here.com/documentation/geocoder-autocomplete/topics/quick-start-get-suggestions.html

[here-dev-rest-batch_geocoder]: https://developer.here.com/documentation/batch-geocoder/topics/quick-start-batch-geocode.html

[here-dev-rest-places]: https://developer.here.com/documentation/places/topics/quick-start-find-text-string.html

[here-dev-rest-routing]: https://developer.here.com/documentation/routing/topics/request-a-simple-route.html

[here-dev-rest-transit]: https://developer.here.com/documentation/transit/topics/what-is.html

[here-dev-rest-im_routing]: https://developer.here.com/documentation/park-and-ride/topics/quick-start-parknride.html

[here-dev-rest-traffic]: https://developer.here.com/documentation/traffic/topics/incident-data.html

[here-dev-rest-fleet]: https://developer.here.com/documentation/fleet-telematics/dev_guide/index.html

[here-dev-rest-weather]: https://developer.here.com/documentation/weather/topics/overview.html

[here-dev-rest-advert]: https://developer.here.com/documentation/at-data-services/dev_guide/index.html

[here-dev-rest-xyz_hub]: https://developer.here.com/documentation/xyz/map_customization_suite_hlp/dev_guide/index.html

[here-dev-rest-tracking]: https://developer.here.com/documentation/tracking/index.html

[here-dev-rest-positioning]: https://developer.here.com/documentation/positioning/topics/request-first-locate.html

[here-dev-plans]: https://developer.here.com/plans

[here-dev-sign-up-osb]: https://developer.here.com/plans?utm_medium=referral&utm_source=GitHub-Service-Broker&create=Freemium

[here-location-services]: https://www.here.com/products/location-based-services

[osb]: https://www.openservicebrokerapi.org

[osb-spec]: https://github.com/openservicebrokerapi/servicebroker/blob/v2.14/spec.md

[youtube-here-osb]: https://www.youtube.com/user/heremaps
