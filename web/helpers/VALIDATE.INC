<SCRIPT type="text/javascript">

function isNumeric(field, msg)
{
   if (isNaN(field.value) || field.value.lastIndexOf(" ") != -1)
   {
      alert("The \"" + msg + "\" field is only allowed to contain digits!");
      field.focus();
      return false;
   }
   return true;
}


//if field1 is given field2 must be given!
function secondFieldIsNotEmpty(field1, field2, msg1, msg2)
{
	if ((field1.value != "") && (field2.options[field2.selectedIndex].value == ""))
	{
     alert("The \"" + msg2 + "\" field must be specified when the \"" + msg1 + "\" field is filled in!");
     field2.focus();
     return false;
   }
   return true;
}

function isValidDates(field, msg)
{
   var i = field.value.substring(0,1);  
   if(parseInt(i) == parseInt(0))
   {
      alert("\"0xxx\" is not a valid year in the \"" + msg + "\" field!");
      return false;
   }
   return true;
}

function isNotNewerDate(inputDate, maxDate, msg, message)
{
   var mess             = parseInt(message);
   var givenDateYear    = inputDate.value.substring(0, 4);
   var givenDateMonth   = inputDate.value.substring(5, 7);
   var givenDateDay     = inputDate.value.substring(8, 10);
   var givenDate        = parseInt(givenDateYear+givenDateMonth+givenDateDay); 
   
   var toDayDateYear    = maxDate.value.substring(0, 4);
   var toDayDateMonth   = maxDate.value.substring(5, 7);
   var toDayDateDay     = maxDate.value.substring(8, 10);
   var toDayDate        = parseInt(toDayDateYear+toDayDateMonth+toDayDateDay);   
     
   if((givenDate > toDayDate) && (mess == 0))
   {
      alert("It is not possible to assign a future date ("+inputDate.value+") to the \"" + msg + "\" field!");
      inputDate.focus();
      return false;
   }
   else if((givenDate < toDayDate) && (mess == 1))
   {
      alert("The expiration date ("+inputDate.value+") must be greater than the registration date.");
      inputDate.focus();
      return false;
   }
   else if((givenDate < toDayDate) && (mess == 2))
   {
      alert("The signed date ("+inputDate.value+") must be greater than the started negotiating date.");
      inputDate.focus();
      return false;
   }
   else if((givenDate < toDayDate) && (mess == 3))
   {
      alert("The valid to date ("+inputDate.value+") must be greater than the started negotiating date.");
      inputDate.focus();
      return false;
   }
   else if((givenDate < toDayDate) && (mess == 4))
   {
      alert("The valid to date ("+inputDate.value+") must be greater than the signed date.");
      inputDate.focus();
      return false;
   }
   else if((givenDate < toDayDate) && (mess == 5))
   {
      alert("The valid to date ("+inputDate.value+") must be greater than the from date.");
      inputDate.focus();
      return false;
   }
   if((givenDate < toDayDate) && (mess == 6))
   {
      alert("It is not possible to assign an old date ("+inputDate.value+") to the \"" + msg + "\" field!");
      inputDate.focus();
      return false;
   }
   if((givenDate < toDayDate) && (mess == 7))
   {
      return false;
   }
   return true
}

function isValidPlusMinus(field, msg)
{
   if (field.value.lastIndexOf("'") != -1 || field.value.lastIndexOf("\"") != -1 || field.value.lastIndexOf("|") != -1 || field.value.lastIndexOf("+") != -1 || field.value.lastIndexOf("-") != -1 || field.value.lastIndexOf("<") != -1 || field.value.lastIndexOf(">") != -1)
   {
      alert("The \"" + msg + "\" field is not allowed to contain the following characters:  \'  \"  +  -  | < >");
      field.focus();
      return false;
   }
   return true;
}

function isValid(field, msg)
{
   if (field.value.lastIndexOf("'") != -1 || field.value.lastIndexOf("\"") != -1 || field.value.lastIndexOf("|") != -1 || field.value.lastIndexOf("<") != -1 || field.value.lastIndexOf(">") != -1)
   {
      alert("The \"" + msg + "\" field is not allowed to contain the following characters:  \'  \"  | < >");
      field.focus();
      return false;
   }
   return true;
}

function isNotEmpty(field, msg)
{
	if (field.value == "")
	{
      alert("The \"" + msg + "\" field is not allowed to be empty!");
      field.focus();
      return false;
   }
   return true;
}
function isValidForOracleReports(field, msg)
{
   if (field.value.lastIndexOf("'") != -1 || field.value.lastIndexOf("\"") != -1 || field.value.lastIndexOf("|") != -1 || field.value.lastIndexOf("<") != -1 || field.value.lastIndexOf(">") != -1 || field.value.lastIndexOf("%") != -1 || field.value.lastIndexOf("+") != -1 || field.value.lastIndexOf("-") != -1)
   {
      alert("The \"" + msg + "\" field is not allowed to contain the following characters:  \'  \"  | < > % + -");
      field.focus();
      return false;
   }
   return true;
}

function isSelected(field, msg)
{
   if (field.options[field.selectedIndex].value == "null")
   {
      alert("There must be a selection in the \"" + msg + "\" drop down box.");
      field.focus();
      return false;
   }
   return true;
}

function isNumberInRange(field, msg)
{
 if (field.value<0 || field.value>100)
   {
      alert("The value must be between 0 and 100 in the \"" + msg + "\" field.");
      field.focus();
      return false;
   }
   return true;
}

function isNotFutureDate(field, msg)
{
 if ( field.value > date )
   {
      alert("The value can not be a future date in the \"" + msg + "\" field.");
      field.focus();
      return false;
   }
   return true;
}

function isLessThenField(field, field2, msg, msg2)
{
   var f = parseInt(field.value);
   var f2 = parseInt(field2.value);
   if (f>f2)
   {
      alert("The \"" + msg + "\" field must be less or the same as the \"" + msg2 + "\" field.");
      field.focus();
      return false;
   }
   return true;
}

</SCRIPT>
