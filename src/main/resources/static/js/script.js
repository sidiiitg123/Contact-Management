console.log("this is script file")

const toggleSidebar =() => {


    if ($(".sidebar").is(":visible")) {
        //band karna hai
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left","0%");
    }else{
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left","20%");
    }

};
function openNav() {
    document.getElementById("mySidebar").style.display = "block";
    document.getElementById("main").style.marginLeft = "20%";
}


function closeSide() {
document.getElementById("main").style.marginLeft="0";
    document.getElementById("mySidebar").style.display="none";
}
