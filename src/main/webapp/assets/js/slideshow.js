// Slideshow
function slideshow() {
    const slideshowList = $(".js-slideshow");
    const thumb = $(".js-slideshow-thumb");
    if (slideshowList && thumb) {
        let slideshowItem = slideshowList.querySelectorAll(".js-slideshow-img");
        if (slideshowItem) {
            slideshowItem.forEach((img) => {
                img.onclick = () => {
                    slideshowItem.forEach((img) => {
                        if (img.classList.contains("active")) {
                            img.classList.remove("active");
                        }
                    });
                    thumb.src = img.src;
                    img.classList.add("active");
                };
            });
        }
    }
}
slideshow();
