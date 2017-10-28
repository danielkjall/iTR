<SCRIPT LANGUAGE=JavaScript>
var gstrREF_CHAR_STRING = "abcdefghijklmnopqrstuvwxyz���";
var gstrREF_INT_STRING = "1234567890";
var gstrREF_CHAR_INT_STRING = "1234567890abcdefghijklmnopqrstuvwxyz���";
var gstrWHITESPACE = " \t\n\r"; // whitespace characters

//-----------------------------------------------------------------------
function sixToEightDate(rstrValue)
/*
Purpose:      sixToEightDate  - changes the date form YYMMDD to YYYYMMDD.
Parameters:   rstrValue - the 6-character date
              
Return value: The date in the format YYYYMMDD
*/
{  
   var retValue = "";
   if (rstrValue.length == 6 ) // if 6 charachters
   {
      if( rstrValue <= 601231 )
      {
         retValue = "20" + rstrValue;
      }
      else
      {
         retValue = "19" + rstrValue;
      } 
   }  
   else if (rstrValue.length == 8)
   {
      retValue = rstrValue;
   }
   var modifiedDate = "";
   if ((rstrValue.length > 8) || (rstrValue.length < 6) || !isInteger(rstrValue))
   {
      modifiedDate = rstrValue;
   }
   else
   {
      modifiedDate = FormatDate(2,retValue);
   }
         
   return(modifiedDate);
}
//-----------------------------------------------------------------------
function isStrValidToMask(rstrValue, rstrMask)  
/*
Purpose:      isStrValidToMask  - Check if string fits the mask.
                                  Enter following characters :
                                     - Y for number
                                     - M for number
                                     - D for number
Parameters:   rstrValue - Check value
              rstrMask  - Mask to compare string with
Return value: if ok     - True, else False
*/
{
var intLenStr = rstrValue.length;
var intLenMask = rstrMask.length;
var intCount;
var strValueChar;
var strMaskChar;

    if ((intLenStr==0) || (intLenMask ==0))
    {
        return(false);
    }
    if (intLenStr!= intLenMask )
    {
        return(false);
    }
    for (intCount=0; intCount <= intLenStr; intCount++)  
    {
        strValueChar = rstrValue.substring(intCount, intCount+1);
        strMaskChar = rstrMask.substring(intCount, intCount+1);
        if ((strMaskChar == 'Y') || (strMaskChar == 'M') || (strMaskChar == 'D'))
        {
            if(!isNumberChar(strValueChar))
            {
                return(false);
            }
        }
        else
        {
            if (strMaskChar!= strValueChar)
            {
                return(false);
            }
        }
    }
    return (true);
}

//-----------------------------------------------------------------------
function GetDatePartByFormat(rstrValue, rstrMask)
/*
Purpose:      GetDatePartByFormat - Returns datepart from maskstring.
                                    Enter following characters:
                                    - Y for YEAR  - YYYY
                                    - M for MONTH - MM
                                    - D for DAY   - DD
                                    - X for not char
Parameters:   rstrValue - Check value
              rstrMask  - Mask to compare string with
Return value: If ok     - value, else empty string
*/
{
var intLenStr = rstrValue.length;
var intLenMask = rstrMask.length;
var intCount;
var strValueChar;
var strMaskChar;
var strReturnValue  = "";
var strReturnString = "";

    if ((intLenStr==0) || (intLenMask ==0))
    {
        return(strReturnValue);
    }
    for (intCount=0; intCount <= intLenStr; intCount++)  
    {
        strValueChar = rstrValue.substring(intCount, intCount+1);
        strMaskChar = rstrMask.substring(intCount, intCount+1);
        if ((strMaskChar == 'Y') || (strMaskChar == 'M') || (strMaskChar == 'D'))
        {
            if(isNumberChar(strValueChar))
            {
                strReturnString = strReturnString + strValueChar;
            }
            else
            {
                return(strReturnValue);
            }
        }
    }
    strReturnValue = strReturnString;
    return (strReturnValue);
}

//-----------------------------------------------------------------------
function isAlphabeticChar(rstrChar)
/*
Purpose:      isAlphabeticChar - Check if rstrChar is an alpha
Parameters:   rstrChar         - Check value
Return value: If ok            - True, else False
*/
{
    if (rstrChar.length != 1)
    {
        return (false);
    }
    rstrChar = rstrChar.toLowerCase();
    return !(gstrREF_CHAR_STRING.indexOf (rstrChar.toLowerCase(), 0) == -1);
}

//-----------------------------------------------------------------------
function isNumberChar(rstrChar)
/*
Purpose:      isNumOrChar - Check if rstrChar is a number
Parameters:   rstrChar    - Check value
Return value: If ok       - True, else False
*/
{
    if (rstrChar.length != 1)
    {
        return (false);
    }
    return !(gstrREF_INT_STRING.indexOf (rstrChar, 0) == -1);
}

//-----------------------------------------------------------------------
function isNumOrChar(rstrChar)
/*
Purpose:      isNumOrChar - Check of rstrChar is alphanumeric
Parameters:   rstrChar    - Check value
Return value: If ok       - True, else False
*/
{
    if (rstrChar.length != 1)
    {
        return (false);
    }
    rstrChar = rstrChar.toLowerCase();
    return !(gstrREF_CHAR_INT_STRING.indexOf (rstrChar, 0) == -1);
}

//-----------------------------------------------------------------------
function isEmpty(rstrValue)
/*
Purpose:      isEmpty   - Check if string is empty
Parameters:   rstrValue - Check value
Return value: If empty  - True, else False
*/
{
    return ((rstrValue == null) || (rstrValue.length == 0));
}

//-----------------------------------------------------------------------
function isWhitespace(rstrValue)
/*
Purpose:      isWhitespace - Check if rstrValue contains any characters
                             except whitspace and null
Parameters:   rstrValue    - Check value
Return value: If empty or whitespace  - True, else False
*/
{   
var intCounter;
var strChar;
    // Is s empty?
    if (isEmpty(rstrValue))
    {
        return true;
    }
    // Search through string's characters one by one
    // until we find a non-whitespace character.
    // When we do, return false; if we don't, return true.
    for (intCounter = 0; intCounter < rstrValue.length; intCounter++)
    {   
        // Check that current character isn't whitespace.
        strChar = rstrValue.charAt(intCounter);
        if (gstrWHITESPACE.indexOf(strChar) == -1)
        {
            return (false);
        }
    }
    // All characters are whitespace.
    return (true);
}

//-----------------------------------------------------------------------
function isEmail(rstrValue)
/*
Purpose:  isEmail  - Check if rstrValue contains valid email address.
		  Email address must be of form a@b.c -- in other words:
		  * there must be at least one character before the @
		  * there must be at least one character before and after the .
		  * the characters @ and . are both required
Parameters:   rstrValue - Check value
Return value: If ok     - True, else False
*/
{
var intCounter = 1;
var intStrLength = rstrValue.length;
   
    // is s whitespace?
    if (isWhitespace(rstrValue))
    {
        return false;
    }
    
    // there must be >= 1 character before @, so we
    // start looking at character position 1 
    // (i.e. second character)
    // look for @
    while ((intCounter < intStrLength) && (rstrValue.charAt(intCounter) != "@"))
    { 
        intCounter++;
    }
    if ((intCounter >= intStrLength) || (rstrValue.charAt(intCounter) != "@"))
    {
        return (false);
    }
    else
    {
        intCounter += 2;
    }
    // look for .
    while ((intCounter < intStrLength) && (rstrValue.charAt(intCounter) != "."))
    { 
        intCounter++;
    }
    // there must be at least one character after the .
    return !((intCounter >=  intStrLength - 1) || (rstrValue.charAt(intCounter) != "."));
}

//-----------------------------------------------------------------------
function isAlpha(rstrValue)
/*
Purpose:      isAlpha   - Validate that this string contains no digits
Parameters:   rstrValue  - Check value
Return value: If alpha  - True,  else False
*/
{
var strChar;
var intCounter;

    for (intCounter = 0; intCounter < rstrValue.length; intCounter++) 
    {
        strChar = rstrValue.substring(intCounter, intCounter + 1);
        if (strChar >= "0" && strChar <= "9")
        {
            return(false);
        }
    }
    return(true);
}

//-----------------------------------------------------------------------
function lenCheck(rstrValue, rintMinLength, rintMaxLength)
/*
Purpose:      lenCheck      - Validate that this string has valid length
Parameters:   rstrValue     - Check value
              rintMinLength - Valid min length
              rintMaxLength - Valid max length
Return value: If ok         - True else False
*/
{
    return !((rstrValue.length < rintMinLength || rstrValue.length > rintMaxLength));
}

//-----------------------------------------------------------------------
function isInteger(rstrValue)
/*
Purpose:      isInteger   - only the numeric values are OK. Accepts + and -
Parameters:   rstrValue   - Check value
Return value: If integer  - True,  Not numeric = False
*/
{
var strChar;
var intStartPos;
var intCounter;

    // skip leading + or -
    if ( (rstrValue.charAt(0) == "-") || (rstrValue.charAt(0) == "+") )
    {
       intStartPos = 1;    
    }
    else
    {
        intStartPos = 0;
    }    
    for(intCounter=intStartPos; intCounter < rstrValue.length; intCounter++)
    {
        strChar = rstrValue.substring(intCounter, intCounter+1);
        if (strChar < "0" || strChar > "9")
        {
            return(false);
        }
    }
    return(true);
}

//-----------------------------------------------------------------------
function internal_isNumeric(rstrValue)
/*
Purpose:      internal_isNumeric   - numeric values and -/,/./+ are OK
Parameters:   rstrValue   - Check value
Return value: If numeric  - True,  Not numeric = False
*/
{
var strChar;
var intCounter;
    for(intCounter=0; intCounter < rstrValue.length; intCounter++)
    {
        strChar = rstrValue.substring(intCounter, intCounter+1);
        if (!((strChar >= "0" && strChar <= "9") || (strChar  == ",") || (strChar == "+") || (strChar == "-") || (strChar == ".")))
        {
            return(false);
        }
    }
    return(true);
}

//-----------------------------------------------------------------------
function internal_isNumberInRange(rstrValue, rlngMinValue, rlngMaxValue)
/*
Purpose:      internal_isNumberInRange - numeric value min and max check
Parameters:   rstrValue       - Check value
              rlngMinValue    - Valid min value
              rlngMaxValue    - Valid max value
Return value: If valid        - True, else False
*/
{
var intNumber = parseInt(rstrValue);
    return ((intNumber >= rlngMinValue) && (intNumber <= rlngMaxValue));
}

//-----------------------------------------------------------------------
function isValidDate(rstrValue)
/*
Purpose:      isValidDate  - Date check function
Parameters:   rstrValue    - Check value (YYYYMMDD)
Return value: valid date   - YYYYMMDD, invalid date = False
                             YY > 50, 1900 YY <= 50, 2000	
*/
{
var intYYYY;
var intMM;
var intDD;
    if (rstrValue.length != 8)                                 
    // invalid length
    {
        return(false);
    }               
    if (internal_isNumeric(rstrValue) == false)
    // not numeric
    {
        return(false);
    }    
    intYYYY = parseInt(rstrValue.substring(0, 4),10);
    if (intYYYY > 50)
    {
        intYYYY = intYYYY + 1900;
    }
    else
    {
        intYYYY = intYYYY + 2000;
    }
    intMM = parseInt(rstrValue.substring(4, 6),10);
    intDD = parseInt(rstrValue.substring(6, 8),10);
    if ((intMM < 1) || (intMM > 12))
    // invalid month
    {
        return(false);
    }        
    if ((intMM == 1) || (intMM == 3) || (intMM == 5) || (intMM == 7) || (intMM == 8) || (intMM == 10) || (intMM == 12))
    {
        if ((intDD < 1) || (intDD > 31))
        // invalid date
        {
            return(false);
        }  
    }
    else
    {
        if ((intDD < 1) || (intDD > 30))
        // invalid date
        {
            return(false);
        }  
    }
    if (intMM == 2)
    // check leap year
    {                                       
        if ((intYYYY % 400 == 0) || ((intYYYY % 4 == 0) && (intYYYY % 100 != 0)))
        {
            if (intDD > 29)
            // invalid date, leap year
            {
                return(false);
            }            
        }
        else
        {
            if (intDD > 28)
            // invalid date, not leap year
            {
                return(false);
            }            
        }
    }	
    return(intYYYY + rstrValue.substring(2, 4) + rstrValue.substring(4, 6));
}

//-----------------------------------------------------------------------
function FormatDate(rintFormat, rstrDate)
/*
Purpose:      FormatDate - edit date based on the format Parameters
Parameters:   rintFormat - 1:YYYY/MM/DD  2:YYYY-MM-DD
              rstrDate   - YYYYMMDD
Return value: result
*/
{	
var v_result = "";
    if (rintFormat == 1)
    {
        v_result = rstrDate.substring(0, 4) + "/" + rstrDate.substring(4, 6) + "/" + rstrDate.substring(6, 8);
    }
    if (rintFormat == 2)
    {
        v_result = rstrDate.substring(0, 4) + "-" + rstrDate.substring(4, 6) + "-" + rstrDate.substring(6, 8);
    }
    return(v_result);
}

//-----------------------------------------------------------------------
function isDateInRange(rstrValue, rstrMinDate, rstrMaxDate)
/*
Purpose:      isDateInRange - date value min and max check
Parameters:   rstrValue     - Check value YYYYMMDD
              rstrMinDate   - Valid min value YYYYMMDD
              rstrMaxDate   - Valid max value YYYYMMDD
Return value: If valid      - True, else False
*/
{
    var v_CurrentDate = new Date(parseInt(rstrValue.substring(0,4),10) - 1900, parseInt(rstrValue.substring(4,6),10) - 1, parseInt(rstrValue.substring(6,8),10));
    var v_MinDate = new Date(parseInt(rstrMinDate.substring(0,4),10) - 1900, parseInt(rstrMinDate.substring(4,6),10) - 1, parseInt(rstrMinDate.substring(6,8),10));
    var v_MaxDate = new Date(parseInt(rstrMaxDate.substring(0,4),10) - 1900, parseInt(rstrMaxDate.substring(4,6),10) - 1, parseInt(rstrMaxDate.substring(6,8),10));
    return ((v_CurrentDate.getTime() >= v_MinDate.getTime() ) && (v_CurrentDate.getTime() <= v_MaxDate.getTime() ))
}

//-----------------------------------------------------------------------
function isSelectionOk(robjForm, rintStartElement, rintCount, rintCountReq, rstrCompare)
/*
Purpose:      isSelectionOk    : this function checks if item is selected
Parameters:   robjForm         : form.. must be this.form
              rintStartElement : pointer for the radio button 
			                     (element number of the first radio button)
              rintCount        : number of item
              rintCountReq     : number: number of item must selected
              rstrCompare      : =:equal  >: greater or equal  <: less or equal
Return value: No error         : true, Error: false
*/
{
var intSelectedCounter = 0;
var v_msg = "";
var intCounter;

    for(intCounter=0; intCounter<rintCount; intCounter++)
    {
        if (robjForm.elements[rintStartElement + intCounter].checked==true)
        {
            intSelectedCounter += 1;
        }
    }
    if (rstrCompare== ">")
    {
        if (intSelectedCounter <= rintCountReq)
        {
            v_msg = "Select more than " + rintCountReq + " items";
        }
    }
    else
    {
        if (rstrCompare == "<")
        {
            if ((intSelectedCounter >= rintCountReq) || (intSelectedCounter == 0))
            {
                v_msg = "Select less than " + rintCountReq + " items";
            }
        }
        else
        {
            if (intSelectedCounter != rintCountReq)
            {
                v_msg = "Select " + rintCountReq + " items";
            }
        }
    }
    if (v_msg == "")
    {
        return(true);
    }
    else
    {
	    robjForm.elements[rintStartElement].focus();
        return(false);
    }
}

function isDate( field, msg )
{
   var blnReturnValue = false;

   if (isWhitespace(field.value))
   {
      alert("The \"" + msg + "\" field is not allowed to be empty!");
      field.focus();
   }
   else if (isStrValidToMask(field.value, 'YYYY-MM-DD'))     
   {
      var tmpDate = "";
      tmpDate = (GetDatePartByFormat(field.value, 'YYYYXXXXXX'))
      tmpDate = tmpDate + (GetDatePartByFormat(field.value, 'XXXXXMMXXX'))
      tmpDate = tmpDate + (GetDatePartByFormat(field.value, 'XXXXXXXXDD'))
      if (! isValidDate(tmpDate))
      {
	      alert("Not a valid date!");
         field.focus();
	   }
	   else
	   {
         blnReturnValue = true;            
	   }
   }
   else
   {
        alert("The date should be on the format YYYY-MM-DD, YYYYMMDD or YYMMDD!");
        field.focus();
   }
   return(blnReturnValue);
}
function isDateOrEmpty( field, msg )
{
   var blnReturnValue = false;

   if (isWhitespace(field.value))
   {
      blnReturnValue = true;
   }
   else if (isStrValidToMask(field.value, 'YYYY-MM-DD'))     
   {
      var tmpDate = "";
      tmpDate = (GetDatePartByFormat(field.value, 'YYYYXXXXXX'))
      tmpDate = tmpDate + (GetDatePartByFormat(field.value, 'XXXXXMMXXX'))
      tmpDate = tmpDate + (GetDatePartByFormat(field.value, 'XXXXXXXXDD'))
      if (! isValidDate(tmpDate))
      {
	      alert("Not a valid date!");
         field.focus();
	   }
	   else
	   {
         blnReturnValue = true;            
	   }
   }
   else
   {
        alert("The date should be empty or on the format YYYY-MM-DD, YYYYMMDD or YYMMDD!");
        field.focus();
   }
   return(blnReturnValue);
}
</SCRIPT>
