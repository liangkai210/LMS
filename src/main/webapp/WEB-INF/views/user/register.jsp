<%@include file="/template.html" %>
<script type="text/javascript">

    function ifValid() {
        var username = document.getElementById("inputName").value;
        if (!username.match("[A-Za-z0-9_]+") || username == null || username.trim().length == 0) {
            alert("Illegal Username!")
            document.getElementById("inputName").focus;
            return false;
        } else {
            return true;
        }

    }

    function searchGenres() {

        $.ajax({
            url: "validateUserName",
            data: {
                title: $('#inputName').val(),
            }
        }).done(function (data) {
            data = JSON.parse(data);
            if(!data["flag"]){
                $("#subButton").hide();
            }else{
                $('#subButton').show();
            }
            //console.log(data["result"]);
            $("#message").html(data["result"]);
        });
    }
    //    var xhr = new XMLHttpRequest();
    //    xhr.onreadystatechange = function () {
    //        if (xhr.readyState === 4) {
    //            if (xhr.status === 200) {
    //            }
    //            document.getElementById('message').innerHTML = xhr.responseText;
    //        }
    //    }
    //    }
    //    ;
    //    xhr.open('GET', 'validateUserName');
    //    function sendAJAX() {
    //        var username = document.getElementById("inputName").value;
    //        console.log(username);
    //        xhr.send();
    //        document.getElementById('load').style.display = "none";
    //    }
</script>
<div class="container">
    <h2>Library Management System</h2>
    <hr>
    <div class="row">
        <div class="col-md-4">
        </div>

        <div class="col-md-4">
            <form class="form-signin" action="registerSuccess" onsubmit="return ifValid()">
                <h5 class="form-signin-heading">Please Register</h5>
                <br> <label class="sr-only">User Name</label>
                <br> <input onkeyup="searchGenres()" for="inputName" name="username" type="text" id="inputName"
                            class="form-control"
                            placeholder="User Name"
                            required autofocus>

                <div id="message"></div>
                <br> <label for="inputPassword" class="sr-only">Password</label>
                <input name="password" type="password" id="inputPassword"
                       class="form-control" placeholder="Password" required>
                <br> <label class="sr-only">Name</label>
                <br> <input name="name" type="text" class="form-control" placeholder="Name" required autofocus>
                <br> <label class="sr-only">Address</label>
                <br> <input name="address" type="text" class="form-control" placeholder="Address" required autofocus>
                <br> <label class="sr-only">Phone</label>
                <br> <input name="phone" type="text" class="form-control" placeholder="Phone Number" required autofocus>

                <button class="btn btn-lg btn-primary btn-block" id = "subButton" type="submit">Register</button>
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>
