$(document).ready(function () {
    let adminTab = $("#adminTab");
    let userTab = $("#userTab");

    let adminTableLink = $(".nav-link[href='#adminTable']");
    let newUserLink = $(".nav-link[href='#newUser']");

    function hideBlocks() {
        adminTableLink.hide();
        newUserLink.hide();
    }

    adminTab.click(function () {
        adminTableLink.show();
        newUserLink.show();
    });

    userTab.click(function () {
        hideBlocks();
    });
});

