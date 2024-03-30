// Default Image
// 전역 함수로 설정
window.imageDefault = function() {
	const images = document.getElementsByTagName("img");

	if(images != null) {
		for (let i = 0; i < images.length; i++) {
		    images[i].height = 500;
		    images[i].onerror = function() {
		        this.onerror = null;
		        this.src = "/img/faker.webp";
		    };
		}
	}
}

// toolbar setting
$("#toolbar").load("../toolbar.jsp");

// font setting
$("body").css("font-family", "DungGeunMo, Arial, Helvetica, sans-serif");

// Default Image 설정
imageDefault();

// check import
console.log("common.js");