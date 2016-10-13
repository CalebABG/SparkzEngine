$(document).ready(function() {
    $("#button1").click(function() {
      $("#site-navigation").toggleClass("main-navigation main-navigation toggled");
    });

    $("li").on("click", function(event){
      console.log('clicked');
    });

    // $("sub-menu").on("click", function(e) {  // See here, i have our selector set to "li", so this jQuery object will grab all li tags on the page
    //     $(this).addClass("current-menu-item").siblings().removeClass("current-menu-item");
    // });
});
