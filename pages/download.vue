<template>
  <ul>
    <li class="download-item" v-for="release in releases" :key="release.title">
      <a :href="release.link" target="_blank">
        <h1>{{ release.title }}</h1>
      </a>

      <div v-html="$markdown(release.description)" class="text-left py-5"></div>

      <a :href="release.link" class="button" target="_blank">Read more</a>
      <a :href="release.download" class="button">Download</a>
    </li>
  </ul>
</template>

<script>
export default {
  data() {
    return {
      releases: [],
    };
  },
  async fetch() {
    fetch("https://api.github.com/repos/Skylyxx/SkriptDocsGenerator/releases", {
      method: "GET",
    })
      .then((response) => {
        return response.json();
      })
      .then((json) => {
        let releases = [];
        json.forEach((element) => {
          let release = {
            title: element["name"],
            description: element['body'],
            link: element["html_url"],
            download: element["assets"][0]["browser_download_url"],
          };
          releases.push(release);
        });
        this.releases = releases;
      });
  },
};
</script>