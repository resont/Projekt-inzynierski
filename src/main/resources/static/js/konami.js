// a key map of allowed keys
var allowedKeys = {
  37: 'left',
  38: 'up',
  39: 'right',
  40: 'down',
  65: 'a',
  66: 'b'
};

// the 'official' Konami Code sequence
var konamiCode = ['up', 'up', 'down', 'down', 'left', 'right', 'left', 'right', 'b', 'a'];

// a variable to remember the 'position' the user has reached so far.
var konamiCodePosition = 0;

// add keydown event listener
document.addEventListener('keydown', function(e) {
  // get the value of the key code from the key map
  var key = allowedKeys[e.keyCode];
  // get the value of the required key from the konami code
  var requiredKey = konamiCode[konamiCodePosition];

  // compare the key with the required key
  if (key == requiredKey) {

    // move to the next key in the konami code sequence
    konamiCodePosition++;

    // if the last key is reached, activate cheats
    if (konamiCodePosition == konamiCode.length) {
      alert("Aber natürlich ist Hans nass, er steht unter dem Wasserfall.");
      emoji();
      konamiCodePosition = 0;
    }
  } else {
    konamiCodePosition = 0;
  }
});

function emoji(){
  (function emojiCursor() {
  
    var possibleEmoji = ["😀", "😂", "😆", "😊"];
    var width = window.innerWidth;
    var height = window.innerHeight;
    var cursor = {x: width/2, y: width/2};
    var particles = [];
    
    function init() {
      bindEvents();
      loop();
    }
    
    // Bind events that are needed
    function bindEvents() {
      document.addEventListener('mousemove', onMouseMove);
      document.addEventListener('touchmove', onTouchMove);
      document.addEventListener('touchstart', onTouchMove);
      
      window.addEventListener('resize', onWindowResize);
    }
    
    function onWindowResize(e) {
      width = window.innerWidth;
      height = window.innerHeight;
    }
    
    function onTouchMove(e) {
      if( e.touches.length > 0 ) {
        for( var i = 0; i < e.touches.length; i++ ) {
          addParticle( e.touches[i].clientX, e.touches[i].clientY, possibleEmoji[Math.floor(Math.random()*possibleEmoji.length)]);
        }
      }
    }
    
    function onMouseMove(e) {    
      cursor.x = e.clientX;
      cursor.y = e.clientY;
      
      addParticle( cursor.x, cursor.y, possibleEmoji[Math.floor(Math.random()*possibleEmoji.length)]);
    }
    
    function addParticle(x, y, character) {
      var particle = new Particle();
      particle.init(x, y, character);
      particles.push(particle);
    }
    
    function updateParticles() {
      
      // Updated
      for( var i = 0; i < particles.length; i++ ) {
        particles[i].update();
      }
      
      // Remove dead particles
      for( var i = particles.length -1; i >= 0; i-- ) {
        if( particles[i].lifeSpan < 0 ) {
          particles[i].die();
          particles.splice(i, 1);
        }
      }
      
    }
    
    function loop() {
      requestAnimationFrame(loop);
      updateParticles();
    }
    
    /**
     * Particles
     */
    
    function Particle() {
  
      this.lifeSpan = 120; //ms
      this.initialStyles ={
        "position": "fixed",
        "top": "0",
        "display": "block",
        "pointerEvents": "none",
        "z-index": "10000000",
        "fontSize": "24px",
        "will-change": "transform"
      };
  
      // Init, and set properties
      this.init = function(x, y, character) {
  
        this.velocity = {
          x:  (Math.random() < 0.5 ? -1 : 1) * (Math.random() / 2),
          y: 1
        };
        
        this.position = {x: x - 10, y: y - 20};
  
        this.element = document.createElement('span');
        this.element.innerHTML = character;
        applyProperties(this.element, this.initialStyles);
        this.update();
        
        document.body.appendChild(this.element);
      };
      
      this.update = function() {
        this.position.x += this.velocity.x;
        this.position.y += this.velocity.y;
        this.lifeSpan--;
        
        this.element.style.transform = "translate3d(" + this.position.x + "px," + this.position.y + "px,0) scale(" + (this.lifeSpan / 120) + ")";
      }
      
      this.die = function() {
        this.element.parentNode.removeChild(this.element);
      }
    }
    
    /**
     * Utils
     */
    
    // Applies css `properties` to an element.
    function applyProperties( target, properties ) {
      for( var key in properties ) {
        target.style[ key ] = properties[ key ];
      }
    }
    
    init();
  })();
}
