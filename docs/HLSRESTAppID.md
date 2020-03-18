# HERE Location Services REST APIs using HERE App ID & App Code type authentication

The HERE Location Services (HLS) REST APIs use HERE App ID and App Code. So, all users of these APIs must obtain authentication and authorization credentials and provide them as values for the parameters app_id and app_code. 

The documents below use the placeholder text {YOUR_APP_ID} and {YOUR_APP_CODE} as placeholders foraccess and authorization credentials. Replace these placeholders with your own HERE credentials to access the API resources.

## HLS Documentaion
| HLS REST APIs | Version | Dcoument Link | API Description |
| :------------- | :-------- | :-------- | :------------- |
| Maps Tile API | 2.1.70.2 | [Developer's Guide][here-rest-doc-maptile] | Show fresh day-time map tiles in multiple styles (e.g. base, aerial) including rendered live-traffic tiles for flow overlay. |
| Maps Image API | 1.6.38.0 | [Developer's Guide][here-rest-doc-mapimg] | Get access to pre-rendered map images already optimized for desktop and mobile devices. |
| Geocoder API | 6.2.181 | [Developer's Guide][here-rest-doc-geocoder] | Convert street addresses to geo-coordinates and vice-versa with forward geocoding, including landmarks, and reverse geocoding. |
| Geocoder Autocomplete API | 6.2.181 | [Developer's Guide][here-rest-doc-geocoderac] | Correct misspelling and get suggestions for you address search with fewer keystrokes. |
| Batch Geocoder API | 6.2.58 | [Developer's Guide][here-rest-doc-geocoderbatch] | Send up to a million lines of address information for geocoding in batch process. |
| Places API | 2.74.0 | [Developer's Guide][here-rest-doc-places] | Send up to a million lines of address information for geocoding in batch process. |
| Routing API | 2.74.0 | [Developer's Guide][here-rest-doc-routing] | Provide precise instructions to a destination using various transport modes (e.g., car, truck, public transit, bicycle) and leveraging different algorithms (e.g., matrix, isoline routing). |
| Traffic API | 6.0.85.5 | [Developer's Guide][here-rest-doc-traffic] | Display map tiles with real-time traffic flow information overlays reflected as colored lines drawn on affected streets and roads and get access to real-time traffic flow and incident data in XML and JSON. |
| Fleet Telematics API | 2.5.10 | [Developer's Guide][here-rest-doc-ftroute] | Advanced location algorithms for complex fleet management use cases. |
| Weather API | 1.2.5 | [Developer's Guide][here-rest-doc-weather] | Give insights into real-time weather forecasts, alerts, and astronomical info for any location. |
| Transit API | 3.3.0 | [Developer's Guide][here-rest-doc-transit] | Get access to features that go beyond simple estimated transit routing like search, next departures, and pedestrian access points. |
| Intermodal Routing API | 1.1.0 | [Developer's Guide][here-rest-doc-imrouting] | Provides alternative routes that combine drive, park, ride and walk to a final destination. |
| Positioning API | 1.7.0 | [Developer's Guide][here-rest-doc-position] | Provides positioning estimates based on global Wi-Fi and Cell coverage. |

[here-rest-doc-imrouting]:http://documentation.developer.here.com/pdf/park_and_ride_hlp/1.1.0/Intermodal%20Routing%20API%20v1.1.0%20Developer's%20Guide.pdf

[here-rest-doc-position]: http://documentation.developer.here.com/pdf/positioning_hlp/1.7.0/Positioning%20API%20v1.7.0%20Developer's%20Guide.pdf

[here-rest-doc-transit]: http://documentation.developer.here.com/pdf/public_transit_hlp/3.3.0/Public%20Transit%20API%20v3.3.0%20Developer's%20Guide.pdf

[here-rest-doc-weather]: http://documentation.developer.here.com/pdf/auto_weather_hlp/1.2.5/Destination%20Weather%20API%20v1.2.5%20Developer's%20Guide.pdf

[here-rest-doc-ftroute]: http://documentation.developer.here.com/pdf/routing_custom_hlp/2.5.10/Fleet%20Telematics%20Custom%20Routes%20v2.5.10%20Developer's%20Guide.pdf

[here-rest-doc-traffic]: http://documentation.developer.here.com/pdf/traffic_hlp/6.0.85.5/Traffic%20API%20v6.0.85.5%20Developer's%20Guide.pdf

[here-rest-doc-maptile]: http://documentation.developer.here.com/pdf/map_tile_hlp/2.1.70.2/Map%20Tile%20API%20v2.1.70.2%20Developer's%20Guide.pdf

[here-rest-doc-mapimg]: http://documentation.developer.here.com/pdf/map_image_hlp/1.6.38.0/Map%20Image%20API%20v1.6.38.0%20Developer's%20Guide.pdf

[here-rest-doc-geocoder]: http://documentation.developer.here.com/pdf/geocoding_hlp/6.2.181/Geocoder%20API%20v6.2.181%20Developer's%20Guide.pdf

[here-rest-doc-geocoderac]: http://documentation.developer.here.com/pdf/geocoding_suggestions_hlp/6.2.181/Geocoder%20Autocomplete%20API%20v6.2.181%20Developer's%20Guide.pdf

[here-rest-doc-geocoderbatch]: http://documentation.developer.here.com/pdf/batch_geocoding_hlp/6.2.58/Batch%20Geocoder%20API%20v6.2.58%20Developer's%20Guide.pdf

[here-rest-doc-places]: http://documentation.developer.here.com/pdf/places_pub/2.74.0/Places%20(Search)%20API%20Public%20v2.74.0%20Developer's%20Guide.pdf

[here-rest-doc-routing]: http://documentation.developer.here.com/pdf/routing_hlp/7.2.105/Routing%20API%20v7.2.105%20Developer's%20Guide.pdf
