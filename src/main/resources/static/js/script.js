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
function deleteContact(cid){
    swal({
        title: "Are you sure?",
        text: "you want to delete this contact!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                window.location="/user/delete/"+cid;
            } else {
                swal("Your contactis safe!");
            }
        });

}
function myfunc(){
    let query=$('#search-input').val();

    if(query===""){
        $(".search-result").hide();
    }else{
        console.log(query);
        let url='http://localhost:8080/search/${query}';
        fetch(url).then((response) =>{
            return response.json();
        }).then((data)=>{
            console.log(data);
            let text=`<div class='list-group'>`
            data.forEach((contact) =>{
                text+=`<a href="/user/${contact.cid}/contact" class="list-group-item list-group-item-action"> ${contact.name}</a>`
            });
            text+=`</div>`
            $(".search-result").html(text);

        });
        $(".search-result").show();
    }
}
