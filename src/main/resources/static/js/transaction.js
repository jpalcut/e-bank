
var home = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;

/**
 * Zpracování captcha před transakcí
 * @param token
 */
function onSubmit(token) {
    processTransaction();
}

/**
 * Zpracování transakce
 */
function processTransaction(){

    var accountNumber = $('#accountNumber').val();
    var bankCode = $('#bankCode').val();

    if(accountNumber === "" || bankCode === ""){
        document.getElementById("new_transaction_form").submit();
    }
    else{
        doesNotAccountExist(accountNumber, bankCode);
    }
}

/**
 * Odeslání transakce bez kontroli existence účtu
 */
function sendTransaction(){
    $("#checkAccountId").val("false");
    $('#myModal').modal('hide');
    document.getElementById("new_transaction_form").submit();
}

/**
 * Kontrola
 *
 * @param accountNumber číslo účtu
 * @param bankCode bankovní číslo
 */
function doesNotAccountExist(accountNumber, bankCode){
    var url = home + "/transaction/check-account";
    var data = "number=" + accountNumber + "&bankCode=" + bankCode;

    $.ajax({
        type: "GET",
        url: url,
        data: data,
        dataType: "html",
        success: function (response) {
            if (response.localeCompare("true") == 0) {
                $('#myModal').modal('show');
            }
            else{
                document.getElementById("new_transaction_form").submit();
            }
        }
    });
}

/**
 * Resetování captcha
 */
function captchaReset(){
    $("#checkAccountId").val("true");
    grecaptcha.reset();
}