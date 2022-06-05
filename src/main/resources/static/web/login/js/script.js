Vue.createApp({
  data() {
      return{
          email:"",
          password:"",
          firsNameRegister:"",
          lastNameRegister:"",
          emailRegister:"",
          passwordRegister:"",
          emailInvalid: false,
          errorMensajePeticion:"",
          errorBoolean: false,
          passwordHidden: true,

      }
  },
  created(){
      
      // axios.post('/api/clients',"firstName=pedro2&lastName=rodriguez&email=pedro@mindhub.com&password=pedro",{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => console.log('registered')) 
  },
  methods:{
      async login(){
          await axios.post('/api/login',`email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => {
              console.log('signed in!!!');location.href ='http://localhost:8080/web/accounts.html';
            }).catch(function (error) 
            {
              if (error.response) {
                //console.log(error.response.data);
                if(error.response.status == 401){
                  //console.log(error.response.status)
                  this.errorMensajePeticion = "Email o contraseña incorrectos"
                };
                //console.log(error.response.headers);
              } else if (error.request) {
                // La petición fue hecha pero no se recibió respuesta
                // `error.request` es una instancia de XMLHttpRequest en el navegador y una instancia de
                // http.ClientRequest en node.js
                //console.log(error.request);
              } else {
                // Algo paso al preparar la petición que lanzo un Error
                console.log('Error', error.message);
              }
              //console.log(error.config);
            });
            
      },
      async register(){
        if(this.emailInvalid == false){
            
            await axios.post('/api/clients',`firstName=${this.firsNameRegister}&lastName=${this.lastNameRegister}&email=${this.emailRegister}&password=${this.passwordRegister}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => {
              axios.post('/api/login',`email=${this.emailRegister}&password=${this.passwordRegister}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => {console.log('signed in!!!');location.href ='http://localhost:8080/web/accounts.html';})
            })
            .catch(function (error) {
              if (error.response) {
                // La respuesta fue hecha y el servidor respondió con un código de estado
                // que esta fuera del rango de 2xx
                //console.log(error.response.data);
                console.log(error.response.status);
                //console.log(error.response.headers);
              } else if (error.request) {
                // La petición fue hecha pero no se recibió respuesta 
                // `error.request` es una instancia de XMLHttpRequest en el navegador y una instancia de
                // http.ClientRequest en node.js
                console.log(error.request);
              } else {
                // Algo paso al preparar la petición que lanzo un Error
                console.log('Error', error.message);
              }
              console.log(error.config);
            });
        
        }
      },
      
      
  },
  computed:{
      validarMail(){
        if(this.emailRegister.includes("@admin.com")){
            this.emailInvalid = true;
        }
        else
            this.emailInvalid = false;
      }
  }
}).mount('#app')

const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");

sign_up_btn.addEventListener("click", () => {
  container.classList.add("sign-up-mode");
});

sign_in_btn.addEventListener("click", () => {
  container.classList.remove("sign-up-mode");
});