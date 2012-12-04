
var html = "text/html";
var encoding = "UTF-8";
var ClientSideResourceManager = Packages.com.google.refine.ClientSideResourceManager;

/*
 * Function invoked to initialize the extension.
 */
function init() {
  var RS = Packages.com.google.refine.RefineServlet;
  RS.registerCommand(module, "export", new Packages.org.galaxy.Export()); 
}

/*
 * Function invoked to handle each request in a custom way.
 */
function process(path, request, response) {


  if (path == "/" || path == "") {
	project_id = Packages.org.galaxy.GalaxyInterface.project_import( request.getParameter("DATA_URL") );
    var context = { 
    	"galaxy_url" : request.getParameter("GALAXY_URL"), 
    	"data_url" : request.getParameter("DATA_URL"), 
    	"project_id" : project_id 
    };
    send(request, response, "index.vt", context);
  }
  
}

function send(request, response, template, context) {
  butterfly.sendTextFromTemplate(request, response, context, template, encoding, html);
}
