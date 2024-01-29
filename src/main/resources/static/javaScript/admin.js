const { createApp } = Vue;

let app = createApp({
  data() {
    return {
      games: [],
      selectedGame: null,
      imageKit: new ImageKit({
        publicKey: "public_wpSKwmQD//W8hKo+Jar+km+Mtyo=",
        urlEndpoint: "https://ik.imagekit.io/qy6v8cnaf"
      }),
      fileInput: null,
      imageURL: null
    };
  },

  created() {
    this.loadData();
  },

  methods: {
    loadData() {
      axios.get("/api/games")
        .then((response) => {
          this.games = response.data;
          console.log(this.games);
        })
        .catch((error) => console.error(error));
    },
    loadFiles(event) {
      this.fileInput = event.target.files[0]
      const result = this.imageKit.upload({
        file: this.fileInput,
        publicKey:this.publicKey,
        fileName: this.fileInput.name,
        folder: "gamehub-games"
      })
      console.log(result);
      this.imageURL = result.url
      console.log(this.imageURL)
    },
    deleteGame(id) {
      axios.delete(`/api/games/${id}`)
        .then((response) => {
          console.log(response.data);
          this.loadData();
        })
        .catch((error) => console.error(error));
    },

  }
});

app.mount("#app");