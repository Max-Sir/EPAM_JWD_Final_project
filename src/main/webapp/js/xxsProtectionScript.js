const replaceVulnerableChars = (array) => {
    let lt = /</g;
    let gt = />/g;
    let ap = /'/g;
    let ic = /"/g;

    array.forEach(function(element)  {
        element.value = element.value
            .replace(lt, "&lt;")
            .replace(gt, "&gt;")
            .replace(ap, "&#39;")
            .replace(ic, "&#34;");
    })
}

const xssProtectionListener = (emenent) => {
    emenent.addEventListener("mousedown", () => {
        const textInput = document.querySelectorAll('input[type="text"]');
        const passwordInput = document.querySelectorAll('input[type="password"]');
        replaceVulnerableChars(textInput);
        replaceVulnerableChars(passwordInput);
    }, false);
}



const submitButtons = document.querySelectorAll('.submit');



submitButtons.forEach(function(button) {
    xssProtectionListener(button);
})