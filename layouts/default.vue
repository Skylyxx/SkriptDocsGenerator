<template>
  <div>
    <Navbar />
    <Nuxt />
  </div>
</template>

<style>
::-webkit-scrollbar {
  width: 10px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: #777;
}

::-webkit-scrollbar-thumb:hover {
  background: #555;
}

::-webkit-scrollbar-corner {
  background: transparent;
}

#output::after {
  content: "plugins/SkriptDocsGenerator/SkDynmap.json";
}
</style>
