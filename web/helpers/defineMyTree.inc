<script type="text/javascript">
// You can find instructions for this file here:
// http://www.treeview.net

// Decide if the names are links or just the icons
USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks

// Decide if the tree is to start all open or just showing the root folders
STARTALLOPEN = 1 //replace 0 with 1 to show the whole tree

USEICONS = 0   // 1 to use folder and link icons in the menu. 0 to have no icons.
// Highlights the lasts clicked link in the menu.
HIGHLIGHT = 1
ICONPATH = 'images/MenuIcons/'
HIGHLIGHT_COLOR = 'white';
HIGHLIGHT_BG    = '#333366'; //'blue';

/*
   Possible values for first argument in >gLnk<:
	R	page target is default frame (right frame)
	T	page target is _top = whole window
	B	page target is _blank = new window
	S	page target is _self = this window
	Rh The http protocol is appended by the script
*/

// Root folder
foldersTree = gFld("<b>Home Page</b>", "../Frames/Start.asp")

   //Time reporting
   aux1 = insFld(foldersTree, gFld("Time Report", ""))
      insDoc(aux1, gLnk("R", "Submitted", "../View/Agreements.aspx"))
      insDoc(aux1, gLnk("R", "Todo", "../View/ViewPaymentTermsCompany.aspx"))
      insDoc(aux1, gLnk("R", "Future", "../View/View.asp"))

   // Settings
   aux1 = insFld(foldersTree, gFld("Settings", ""))
      insDoc(aux1, gLnk("R", "Change Password", "../../content/Scorecard/GeneralData.asp"))
      insDoc(aux1, gLnk("R", "User Settings", "../../content/SupplierContact/SupplierContact.asp"))

   // Administration
   aux1 = insFld(foldersTree, gFld("Administration", ""))
      insDoc(aux1, gLnk("R", "Approve Weeks", "../../content/Scorecard/GeneralData.asp"))

   // Report
   aux1 = insFld(foldersTree, gFld("Reports", ""))
      insDoc(aux1, gLnk("R", "Big report", "../../content/Scorecard/GeneralData.asp"))

   //Contact
   insDoc(foldersTree, gLnk("R", "<b>Contact</b>", "mailto:info@intiro.se"));
   //Help
   insDoc(foldersTree, gLnk("R", "<b>Help</b>", "../help/help.asp"));
   //Logout
 insDoc(foldersTree, gLnk("T", "<b>Logout</b>", "http://<%request.getServerName()%>/content/login/logout.jsp"));
</script>