(function ($) {


    $(function () {

        $('.main.menu').visibility({
            type: 'fixed'
        });
        $('.overlay').visibility({
            type: 'fixed',
            offset: 80
        });

        $('.overlay.example .overlay')
            .visibility({
                type: 'fixed',
                offset: 15 // give some space from top of screen
            })
        ;
        /*$('.slider').slider({full_width: true});
         $('.button-collapse').sideNav();
         $('ul.tabs').tabs();
         $('.scrollspy').scrollSpy();*/
        $('.special.cards .image').dimmer({
            on: 'hover'
        });

    }); // end of document ready
})(jQuery); // end of jQuery name space