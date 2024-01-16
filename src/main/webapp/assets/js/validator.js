// Validate Form

function Validator(formSelector) {
    const _this = this;
    const formElement = document.querySelector(formSelector);
    const formRules = {};
    const validatorRules = {
        required(value) {
            return value ? undefined : "Please enter this field";
        },
        email(value) {
            var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            return regex.test(value) ? undefined : "Email is not in correct format";
        },
        phone(value) {
            var regex = /^(0|\+?84)(3|5|7|8|9)[0-9]{8}$/;
            return regex.test(value) ? undefined : "Phone number is not in correct format";
        },
        minLength(min) {
            return function (value) {
                return value.length >= min ? undefined : `Please enter at least ${min} characters!`;
            };
        },
        maxLength(max) {
            return function (value) {
                return value.length <= max ? undefined : `Please enter at most ${max} characters!`;
            };
        },
        confirm(selector) {
            const passwordElement = formElement.querySelector(selector);
            if (passwordElement) {
                return function (value) {
                    return value === passwordElement.value ? undefined : "Please enter your password correctly!";
                };
            }
        },
        integer(value) {
            return value > 0 ? undefined : "input must be greater than 0";
        },
        isNonNegative(value) {
            return (value >= 0 && !isNaN(value)) ? undefined : "input must must be a non-negative number";
        }
    };
    if (formElement) {
        const inputs = formElement.querySelectorAll("[name][rules]");
        Array.from(inputs).forEach(function (input) {
            var rules = input.getAttribute("rules").split("|");
            for (let rule of rules) {
                let ruleInfo, ruleFunc;
                if (rule.includes(":")) {
                    ruleInfo = rule.split(":");
                    rule = ruleInfo[0];
                    ruleFunc = validatorRules[rule](ruleInfo[1]);
                } else {
                    ruleFunc = validatorRules[rule];
                }

                if (Array.isArray(formRules[input.name])) {
                    formRules[input.name].push(ruleFunc);
                } else {
                    formRules[input.name] = [ruleFunc];
                }
            }

            input.onblur = handleValidate;
            input.oninput = handleOnInput;
            input.onchange = function () {
                onChangeValue(input, rules);
            };
        });

        function handleValidate(event) {
            const rules = formRules[event.target.name];
            let errorMessage;
            for (let rule of rules) {
                switch (event.target.type) {
                    case "checkbox":
                    case "radio":
                        let inputChecked = formElement.querySelector(
                            `input[name="${event.target.name}"][rules]:checked`
                        );
                        errorMessage = rule(inputChecked);
                        break;
                    default:
                        errorMessage = rule(event.target.value);
                }
                if (errorMessage) break;
            }
            if (errorMessage) {
                showError(event.target, errorMessage);
            } else {
                hideError(event.target);
            }
            return !!errorMessage;
        }

        function showError(element, errorMessage) {
            const parentElement = element.closest(".form__group");
            if (parentElement) {
                const formMessage = parentElement.querySelector(".form__message");
                if (formMessage) {
                    formMessage.innerText = errorMessage;
                    parentElement.classList.add("invalid");
                }
            }
        }

        function hideError(element) {
            const parentElement = element.closest(".form__group");
            if (parentElement) {
                const formMessage = parentElement.querySelector(".form__message");
                if (formMessage) {
                    formMessage.innerText = "";
                    parentElement.classList.remove("invalid");
                }
            }
        }

        function handleOnInput(event) {
            hideError(event.target);
        }

        function onChangeValue(inputElement, rules) {
            const parentElement = inputElement.closest(".form__group");
            rules.forEach(function (rule) {
                if (parentElement) {
                    const formMessage = parentElement.querySelector(".form__message");
                    if (formMessage) {
                        handleValidate({ target: inputElement });
                    } else {
                        formMessage.innerText = "";
                        parentElement.classList.remove("invalid");
                    }
                }
            });
        }

        function showPassword() {
            const togglePassword_1 = formElement.querySelector("#togglePassword");
            const toggleCurrentPassword = formElement.querySelector("#toggleCurrentPassword");
            const togglePassword_2 = formElement.querySelector("#togglePasswordConfirm");
            const password = formElement.querySelector("#password");
            const currentPassword = formElement.querySelector("#current-password");
            const passwordConfirm = formElement.querySelector("#password_confirmation");
            if (togglePassword_1) {
                togglePassword_1.addEventListener("click", function (e) {
                    let type;
                    if (password.getAttribute("type") === "password" ) {
                        type = "text";
                        password.setAttribute("type", type);
                        this.src = "./assets/icons/eye-slash.svg";
                    } else {
                        type = "password";
                        password.setAttribute("type", type);
                        this.src = "./assets/icons/eye.svg";
                    }
                });
            }
            if (toggleCurrentPassword) {
                toggleCurrentPassword.addEventListener("click", function (e) {
                    let type;
                    if (currentPassword.getAttribute("type") === "password") {
                        type = "text";
                        currentPassword.setAttribute("type", type);
                        this.src = "./assets/icons/eye-slash.svg";
                    } else {
                        type = "password";
                        currentPassword.setAttribute("type", type);
                        this.src = "./assets/icons/eye.svg";
                    }
                });
            }
            if (togglePassword_2) {
                togglePassword_2.addEventListener("click", function (e) {
                    let type;
                    if (passwordConfirm.getAttribute("type") === "password") {
                        type = "text";
                        passwordConfirm.setAttribute("type", type);
                        this.src = "./assets/icons/eye-slash.svg";
                    } else {
                        type = "password";
                        passwordConfirm.setAttribute("type", type);
                        this.src = "./assets/icons/eye.svg";
                    }
                });
            }
        }
        showPassword();
    }

    // Xử lí hành vi submit form
    formElement.onsubmit = function (e) {
        e.preventDefault();

        let isFormValid = true;

        const inputs = formElement.querySelectorAll("[name][rules]");
        Array.from(inputs).forEach(function (input) {
            let isInvalid = handleValidate({ target: input });
            if (isInvalid) {
                isFormValid = false;
            }
        });

        if (isFormValid) {
            if (typeof _this.onSubmit === "function") {
                const enableInputs = formElement.querySelectorAll("[name]:not([disabled])");

                const formValues = Array.from(enableInputs).reduce(function (values, input) {
                    switch (input.type) {
                        case "radio":
                            if (input.matches(":checked")) {
                                values[input.name] = input.value;
                            } else if (!values[input.name]) {
                                values[input.name] = "";
                            }
                            break;
                        // values[input.name] = formElement.querySelector('input[name="' + input.name + '"]:checked').value
                        // break
                        case "checkbox":
                            if (input.matches(":checked")) {
                                if (!Array.isArray(values[input.name])) {
                                    values[input.name] = [];
                                }
                                values[input.name].push(input.value);
                            } else if (!Array.isArray(values[input.name])) {
                                values[input.name] = "";
                            }
                            break;
                        // case 'checkbox':
                        //     if (!input.matches(':checked')) {
                        //         if (!Array.isArray(values[input.name])){
                        //         values[input.name] = ''
                        //        }
                        //         return values
                        //     }

                        //     if (!Array.isArray(values[input.name])) {
                        //         values[input.name] = []
                        //     }

                        //     values[input.name].push(input.value)
                        //     break

                        case "file":
                            values[input.name] = input.files;
                            break;
                        default:
                            values[input.name] = input.value;
                    }
                    return values;
                }, {});
                _this.onSubmit(formValues);
            } else {
                formElement.submit();
            }
        }
    };
}
