$('.heightMatch').matchHeight();
/**
 * adding class active for image slider
 */
var projectSlides = document.getElementsByClassName('carousel-item');
if (projectSlides) {
    projectSlides[0].classList.add("active");
}
/**
 * function to add ellipses if word count is more than 500.
 */
$(document).ready(function() {
    var numberOfCharactersToShow = 500;
    var ellipses = "...";
    $('.projectDescription').each(function() {
        var content = $(this).html();
        if (content.length > numberOfCharactersToShow) {
            var contentToShow = content.substr(0, numberOfCharactersToShow);
            var html = contentToShow + '<span class="moreelipses">' + ellipses + '</span>';
            $(this).html(html);
        }
    });
});