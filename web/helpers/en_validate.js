function isNumeric(field, msg) {
   var strChar;
   var intCounter;
   var tmp = field.value;

   for(intCounter=0; intCounter < tmp.length; intCounter++) {
      strChar = tmp.substring(intCounter, intCounter+1);
      if (!((strChar >= "0" && strChar <= "9") || (strChar === "+") || (strChar === "-") || (strChar === ".") || (strChar === ","))) {
         alert("The \"" + msg + "\" field is only allowed to contain digits and '.' or leading '+' and '-'!");
         field.focus();
         return(false);
      }
   }
   return(true);
}

function isValid(field, msg) {
   if (field.value.lastIndexOf("'") !== -1 || field.value.lastIndexOf("\"") !== -1 || field.value.lastIndexOf("|") !== -1 || field.value.lastIndexOf("<") !== -1 || field.value.lastIndexOf(">") !== -1) {
      alert("The \"" + msg + "\" field is not allowed to contain the following characters:  \'  \"  | < >");
      field.focus();
      return false;
   }
   return true;
}

function isNotEmpty(field, msg) {
   if (field.value === "") {
      alert("The \"" + msg + "\" field is not allowed to be empty!");
      field.focus();
      return false;
   }
   return true;
}

function isSelected(field, msg) {
   if (field.options[field.selectedIndex].value === "null") {
      alert("There must be a selection in the \"" + msg + "\" drop down box.");
      field.focus();
      return false;
   }
   return true;
}

function isNumberInRange(field, msg, lower, upper) {
   if (field.value<lower || field.value>upper) {
      alert("The value must be between " + lower + " and " + upper + " in the \"" + msg + "\" field.");
      field.focus();
      return false;
   }
   return true;
}

function isNotToLong(field, msg, maxLength) {
   var tmp = field.value;
   if (tmp.length > maxLength) {
      alert("The entry in the \"" + msg + "\" field is to long. Max length is " + maxLength + " characters.");
      field.focus();
      return false;
   }
   return true;
}