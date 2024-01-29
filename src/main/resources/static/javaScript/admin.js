const { createApp } = Vue;

let app = createApp({
  data() {
    return {
      games: [],
      selectedGame: null,
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