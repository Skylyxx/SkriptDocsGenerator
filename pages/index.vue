<template>
  <div class="container">
    <div id="termynal" data-termynal>
      <span data-ty="input" data-ty-prompt=">">skriptdocsgenerator</span>
      <span data-ty>
        <span class="text-green-500">
          SkriptDocsGenerator
          <span class="text-yellow-500">»</span>
          Documentation generated for SkDynmap</span
        >
      </span>
    </div>

    <div id="output" class="opacity-0 duration-300" data-termynal>
      <pre class="overflow-scroll" style="max-height: 300px">
          {
            "effects": [
              {
                "id": "EffClearRenders",
                "name": "Clear renders",
                "description": [
                  "Clear all areas and markers on the map"
                ],
                "patterns": [
                  "(wipe|clear) [sk]dynmap render[s]"
                ],
                "examples": [
                  "wipe skdynmap renders"
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "EffCreateArea",
                "name": "Create area",
                "description": [
                  "Create an area and show it on the map"
                ],
                "patterns": [
                  "create area [from] %areabuilder%"
                ],
                "examples": [
                  "command /createarea:\n\ttrigger:\n\t\tcreate area from new area named \"Hello World\" with description \"Hi !\" at {pos-1} and {pos-2}\n\t\tsend \"Area created !\""
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "EffCreateMarker",
                "name": "Create marker",
                "description": [
                  "Create a marker and show it on the map"
                ],
                "patterns": [
                  "create marker [from] %markerbuilder%"
                ],
                "examples": [
                  "command /createmarker:\n\ttrigger:\n\t\tset {_builder} to new marker named \"Marker\" at {pos-1} with icon \"house\"\n\t\tcreate marker {_builder}\n\t\tsend \"Marker created !\""
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "EffDeleteArea",
                "name": "Delete area",
                "description": [
                  "Delete an existent area"
                ],
                "patterns": [
                  "delete area [from] %dynmaparea%"
                ],
                "examples": [
                  "command /deletearea &lt;text&gt;:\n\ttrigger:\n\t\tdelete area from area with id arg-1\n\t\tsend \"Deleted !\""
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "EffDeleteMarker",
                "name": "Delete marker",
                "description": [
                  "Delete an existent marker"
                ],
                "patterns": [
                  "delete marker [from] %dynmapmarker%"
                ],
                "examples": [
                  "command /deletemarker &lt;text&gt;:\n\ttrigger:\n\t\tdelete marker from marker with id arg-1\n\t\tsend \"Deleted !\""
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "EffMakeRender",
                "name": "Make render",
                "description": [
                  "Make the render of areas or markers or a global render"
                ],
                "patterns": [
                  "[make] render [of] %dynmapareas/dynmapmarkers%",
                  "[make] global render [of [sk]dynmap]"
                ],
                "examples": [
                  "command /renderall:\n\ttrigger:\n\t\tmake global render of skdynmap"
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              }
            ],
            "expressions": [
              {
                "id": "ExprDefaultStyle",
                "name": "Default style",
                "description": [
                  "Returns the default style defined in the config."
                ],
                "patterns": [
                  "default [area] style"
                ],
                "examples": [
                  "set {_style} to default style"
                ],
                "since": [
                  "1.0-beta02"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprMarkerWithID",
                "name": "Marker with id",
                "description": [
                  "Get an marker from its id"
                ],
                "patterns": [
                  "[dynmap] marker with id %string%"
                ],
                "examples": [
                  "set {_marker} to marker with id \"my_marker_id\""
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprAllMarkers",
                "name": "All markers",
                "description": [
                  "Return a list with all created markers"
                ],
                "patterns": [
                  "all [dynmap] markers"
                ],
                "examples": [
                  "command /deleteall:\n\ttrigger:\n\t\tloop all markers:\n\t\t\tdelete marker loop-value"
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprMarkerBuilder",
                "name": "Marker builder",
                "description": [
                  "The constructor for a future marker"
                ],
                "patterns": [
                  "new [dynmap] marker named %string% at %locations%",
                  "new [dynmap] marker named %string% at %locations% with [icon] %string%",
                  "new [dynmap] marker with id %string% named %string% at %locations%",
                  "new [dynmap] marker with id %string% named %string% at %locations% with [icon] %string%"
                ],
                "examples": [
                  "set {_builder} to new marker with id \"custom_id\" named \"Landmark\" at {pos-1}"
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprLastMarker",
                "name": "The marker",
                "description": [
                  "This expression returns the last generated marker using marker scope"
                ],
                "patterns": [
                  "[the] [last] [(generated|created)] marker"
                ],
                "examples": [
                  "command /makemarker:\n\ttrigger:\n\t\tmake marker:\n\t\t\tset name of marker to \"Landmark\"\n\t\t\tset location of marker to {pos-2}\n\t\t\tset marker's icon to \"house\"\n\t\tcreate marker from last generated marker"
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprLocations",
                "name": "Locations of area",
                "description": [
                  "Returns the locations of an area/areabuilder\nIt can be get, and set."
                ],
                "patterns": [
                  "[all] [the] locations of %dynmaparea/areabuilder%",
                  "[all] [the] %dynmaparea/areabuilder%'[s] locations"
                ],
                "examples": [
                  "set locations of {_area} to {pos-1} and {pos-2}"
                ],
                "since": [
                  "1.0.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprChunksToLocations",
                "name": "Corners of chunks",
                "description": [
                  "Get the locations from a list of chunks, to be used with locations of areas"
                ],
                "patterns": [
                  "corners of %chunks%"
                ],
                "examples": [
                  "command /makechunks:\n\ttrigger:\n\t\tmake area:\n\t\t\tset id of area to \"chunk_id\"\n\t\t\tset name of area to \"Chunks\"\n\t\t\tset locations of area to corners of {chunk::*}\n\t\tcreate area from last area\n\t\tsend \"Ok!\""
                ],
                "since": [
                  "1.2"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprAreaWithID",
                "name": "Area with id",
                "description": [
                  "Get an area from its id"
                ],
                "patterns": [
                  "[dynmap] area with id %string%"
                ],
                "examples": [
                  "set {_area} to area with id \"my_area_id\""
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprAllAreas",
                "name": "All areas",
                "description": [
                  "Return a list with all created areas"
                ],
                "patterns": [
                  "all [dynmap] areas"
                ],
                "examples": [
                  "command /deleteall:\n\ttrigger:\n\t\tloop all areas:\n\t\t\tdelete area loop-value"
                ],
                "since": [
                  "1.0.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprLastArea",
                "name": "The area",
                "description": [
                  "This expression returns the last generated area using area scope"
                ],
                "patterns": [
                  "[the] [last] [(generated|created)] area"
                ],
                "examples": [
                  "command /makearea:\n\ttrigger:\n\t\tmake area:\n\t\t\tset name of area to \"My Area\"\n\t\t\tset description of area to \"Using sections !\"\n\t\t\tset locations of area to {pos-1} and {pos-2}\n\t\t\tset {_style} to default area style\n\t\t\tset line color of {_style} to \"##00FF00\"\n\t\t\tset fill color of {_style} to \"##FFFF00\"\n\t\t\tset style of area to {_style}\n\t\tcreate area from last generated area\n\t\tsend \"Area created !\""
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprAreaBuilder",
                "name": "Area builder",
                "description": [
                  "The constructor for a future area"
                ],
                "patterns": [
                  "new [dynmap] area named %string% at %locations%",
                  "new [dynmap] area named %string% at %locations% with [style] %areastyle%",
                  "new [dynmap] area named %string% with description %string% at %locations%",
                  "new [dynmap] area named %string% with description %string% at %locations% with [style] %areastyle%",
                  "new [dynmap] area with id %string% named %string% at %locations%",
                  "new [dynmap] area with id %string% named %string% at %locations% with [style] %areastyle%",
                  "new [dynmap] area with id %string% named %string% with description %string% at %locations%",
                  "new [dynmap] area with id %string% named %string% with description %string% at %locations% with [style] %areastyle%"
                ],
                "examples": [
                  "set {_builder} to new area with id \"custom_id\" named \"Blabla\" at ({pos-1}, {pos-2} and {pos-3}) with style {_style}"
                ],
                "since": [
                  "1.0.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprLineWeight",
                "name": "Line weight",
                "description": [
                  "Returns the line-weight property of the provided style."
                ],
                "patterns": [
                  "[the] line(-| )weight of %areastyle%",
                  "%areastyle%'[s] line(-| )weight"
                ],
                "examples": [
                  "set {_lineweight} to line weight of {_style}"
                ],
                "since": [
                  "1.0-beta02"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprLineOpacity",
                "name": "Line opacity",
                "description": [
                  "Returns the line-opacity property of the provided style."
                ],
                "patterns": [
                  "[the] line(-| )opacity of %areastyle%",
                  "%areastyle%'[s] line(-| )opacity"
                ],
                "examples": [
                  "set {_lineopacity} to line opacity of {_style}"
                ],
                "since": [
                  "1.0-beta02"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprLineColor",
                "name": "Line color",
                "description": [
                  "Returns the line-color property of the provided style."
                ],
                "patterns": [
                  "[the] line(-| )color of %areastyle%",
                  "%areastyle%'[s] line(-| )color"
                ],
                "examples": [
                  "set {_linecolor} to line-color of {_style}"
                ],
                "since": [
                  "1.0-beta02"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprFillOpacity",
                "name": "Fill opacity",
                "description": [
                  "Returns the fill-opacity property of the provided style."
                ],
                "patterns": [
                  "[the] fill(-| )opacity of %areastyle%",
                  "%areastyle%'[s] fill(-| )opacity"
                ],
                "examples": [
                  "set {_fillopacity} to fill opacity of {_style}"
                ],
                "since": [
                  "1.0-beta02"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprFillColor",
                "name": "Fill color",
                "description": [
                  "Returns the fill-color property of the provided style."
                ],
                "patterns": [
                  "[the] fill(-| )color of %areastyle%",
                  "%areastyle%'[s] fill(-| )color"
                ],
                "examples": [
                  "set {_fillcolor} to fill color of {_style}"
                ],
                "since": [
                  "1.0-beta02"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprVisibility",
                "name": "Visibility of player",
                "description": [
                  "Get the visibility of a player on the map.\nIt can be set or reset."
                ],
                "patterns": [
                  "[the] visibility of %player%",
                  "%player%'[s] visibility"
                ],
                "examples": [
                  "command /show:\n\ttrigger:\n\t\tset player's visibility to true\n\t\tsend \"You are now visible on the map!\""
                ],
                "since": [
                  "1.2"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprLocation",
                "name": "Location of marker",
                "description": [
                  "Returns the location of a marker/markerbuilder\nIt can be get, or set."
                ],
                "patterns": [
                  "[the] location of %dynmapmarker/markerbuilder%",
                  "%dynmapmarker/markerbuilder%'[s] location"
                ],
                "examples": [
                  "set location of {_marker} to {pos-1}"
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprIcon",
                "name": "Icon of marker",
                "description": [
                  "Returns the icon of a marker/markerbuilder\nIt can be get, set or reset.\nThere is a list of all default dynmap icons: https://github.com/webbukkit/dynmap/wiki/Using-markers#marker-icons"
                ],
                "patterns": [
                  "[the] icon of %dynmapmarker/markerbuilder%",
                  "%dynmapmarker/markerbuilder%'[s] icon"
                ],
                "examples": [
                  "set icon of {_marker} to \"house\""
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprName",
                "name": "Name of Area/Marker",
                "description": [
                  "Returns the name of a area/areabuilder/marker/markerbuilder\nIt can be get, or set"
                ],
                "patterns": [
                  "[the] name of %dynmaparea/areabuilder/dynmapmarker/markerbuilder%",
                  "%dynmaparea/areabuilder/dynmapmarker/markerbuilder%'[s] name"
                ],
                "examples": [
                  "set name of {_area} to \"New Name\""
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprId",
                "name": "Id of Area/Marker",
                "description": [
                  "Returns the id of a area/areabuilder/marker/markerbuilder\nIt can be get, or set"
                ],
                "patterns": [
                  "[the] id of %dynmaparea/areabuilder/dynmapmarker/markerbuilder%",
                  "%dynmaparea/areabuilder/dynmapmarker/markerbuilder%'[s] id"
                ],
                "examples": [
                  "set id of {_area} to \"new_id\""
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprStyle",
                "name": "Style of area",
                "description": [
                  "Returns the style of an area/areabuilder\nIt can be get, set or reset."
                ],
                "patterns": [
                  "[the] [area]style of %dynmaparea/areabuilder%",
                  "%dynmaparea/areabuilder%'[s] [area]style"
                ],
                "examples": [
                  "reset style of {_area}"
                ],
                "since": [
                  "1.0.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ExprDescription",
                "name": "Description of area",
                "description": [
                  "Returns the description of an area/areabuilder\nIt can be get, set, reset or removed."
                ],
                "patterns": [
                  "[the] description of %dynmaparea/areabuilder%",
                  "%dynmaparea/areabuilder%'[s] description"
                ],
                "examples": [
                  "set description of {_area} to \"Hello World\" !"
                ],
                "since": [
                  "1.0.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              }
            ],
            "conditions": [
              {
                "id": "CondIsPlayerVisible",
                "name": "Is player visible",
                "description": [
                  "Check if a player is visible on the map"
                ],
                "patterns": [
                  "%player% is visible",
                  "%player% isn't visible"
                ],
                "examples": [
                  "command /isvisible &lt;player&gt;:\n\ttrigger:\n\t\tif arg-1 is visible:\n\t\t\tsend \"yes\"\n\t\telse:\n\t\t\tsend \"no\""
                ],
                "since": [
                  "1.2"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ScopeMakeArea",
                "name": "Make Area",
                "description": [
                  "This is a scope that allows you to create areas easily"
                ],
                "patterns": [
                  "make [new] [dynmap] area"
                ],
                "examples": [
                  "command /make:\n\ttrigger:\n\t\tmake area:\n\t\t\tset name of area to \"My Area\"\n\t\t\tset description of area to \"Using sections !\"\n\t\t\tset locations of area to {pos-1} and {pos-2}\n\t\t\tset {_style} to default area style\n\t\t\tset line color of {_style} to \"##00FF00\"\n\t\t\tset fill color of {_style} to \"##FFFF00\"\n\t\t\tset style of area to {_style}\n\t\tcreate area from last generated area"
                ],
                "since": [
                  "1.0.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              },
              {
                "id": "ScopeMakeMarker",
                "name": "Make Marker",
                "description": [
                  "This is a scope that allows you to create markers easily"
                ],
                "patterns": [
                  "make [new] [dynmap] marker"
                ],
                "examples": [
                  "command /make:\n\ttrigger:\n\t\tmake marker:\n\t\t\tset name of marker to \"Landmark\"\n\t\t\tset location of marker to {pos-2}\n\t\t\tset marker's icon to \"house\"\n\t\tcreate marker from last generated marker"
                ],
                "since": [
                  "1.1"
                ],
                "requiredPlugins": [
                  "dynmap"
                ]
              }
            ],
            "events": [
              {
                "id": "EventRender",
                "name": "On SkDynmap render",
                "patterns": [
                  "[[sk]dynmap] [map] render"
                ],
                "since": [
                  "1.2"
                ]
              }
            ],
            "types": [
              {
                "id": "dynmaparea",
                "name": "SkDynmap DynmapArea",
                "description": [
                  "SkDynmap's DynmapArea object"
                ],
                "patterns": [
                  "dynmaparea"
                ],
                "since": [
                  "1.0-beta02"
                ]
              },
              {
                "id": "areastyle",
                "name": "SkDynmap AreaStyle",
                "description": [
                  "SkDynmap's AreaStyle object"
                ],
                "patterns": [
                  "areastyle"
                ],
                "since": [
                  "1.0-beta02"
                ]
              },
              {
                "id": "areabuilder",
                "name": "SkDynmap AreaBuilder",
                "description": [
                  "SkDynmap's AreaBuilder object"
                ],
                "patterns": [
                  "areabuilder"
                ],
                "since": [
                  "1.0.1"
                ]
              },
              {
                "id": "markerbuilder",
                "name": "SkDynmap MarkerBuilder",
                "description": [
                  "SkDynmap's MarkerBuilder object"
                ],
                "patterns": [
                  "markerbuilder"
                ],
                "since": [
                  "1.1"
                ]
              },
              {
                "id": "dynmapmarker",
                "name": "SkDynmap DynmapMarker",
                "description": [
                  "SkDynmap's DynmapMarker object"
                ],
                "patterns": [
                  "dynmapmarker"
                ],
                "since": [
                  "1.1"
                ]
              }
            ]
          }
        </pre
      >
    </div>
  </div>
</template>

<script>
export default {
  head() {
    return {
      link: [{ rel: "stylesheet", href: "/css/termynal.css" }],
      script: [
        { src: 'js/termynal.js', 'data-termynal-container': '#termynal' },
        { src: 'js/index.js' }
      ]
    };
  },
};
</script>