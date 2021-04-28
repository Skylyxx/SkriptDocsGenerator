<template>
    <div class="text-center my-60">
        <h1 class="text-3xl">Error {{ error.statusCode }}</h1>
        <p class="text-xl">It looks like you got lost. <NuxtLink to="/" class="text-green-500 hover:text-white hover:underline">Go back home</NuxtLink></p>
    </div>
</template>

<script>
  export default {
    head() {
        return {
            title: `SkriptDocsGenerator - Error ${this.error.statusCode}`
        };
    },
    props: ['error'],
    layout: 'error' // you can set a custom layout for the error page
  }
</script>