function goto(location){
    window.location=location
}

function checkSecid(){
    var elem = document.getElementById("secid");
    var re = /^[a-zA-Z0-9]{4,18}$/;
    return re.test(elem.value);
}

function checkRegnumber(){
    var elem = document.getElementById("regnumber");
    var re = /^\d-\d{2}-\d{5}-[A-Z]$/;
    return re.test(elem.value);
}

function checkName(elem){
    var re = /^["-?!,.а-яА-ЯёЁ0-9\sa-zA-Z()]{3,}$/;
    return re.test(elem.value);
}

function validateSecuritiesForm(){
    return true;
    var secidError = document.getElementById("secidError");
    var regnumberError = document.getElementById("regnumberError");
    var nameError = document.getElementById("nameError");
    var emitentTitleError = document.getElementById("emitentTitleError");

    secidError.hidden = true;
    regnumberError.hidden = true;
    nameError.hidden = true;
    emitentTitleError.hidden = true;

    var result = true;

    if(checkSecid() == false){
        secidError.hidden=false;
        result=false;
    }
    if(checkRegnumber() == false){
        regnumberError.hidden=false;
        result=false;
    }
    if(checkName(document.getElementById("name")) == false){
        nameError.hidden=false;
        result=false;
    }
    if(checkName(document.getElementById("emitentTitle")) == false){
        emitentTitleError.hidden=false;
        result=false;
    }

    return result;
}