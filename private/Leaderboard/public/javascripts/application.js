// Place your application-specific JavaScript functions and classes here
// This file is automatically included by javascript_include_tag :defaults

function mark_for_destroy(element) { 
  $(element).next('.should_destroy').value = 1 
  $(element).up('.member').hide(); 
}

function closeAndRefreshParent()
{
	window.opener.location.href = window.opener.location.href;

  if (window.opener.progressWindow)

 {
    window.opener.progressWindow.close()
  }
  window.close();
	//window.close();
	//window.opener.location.reload();
}