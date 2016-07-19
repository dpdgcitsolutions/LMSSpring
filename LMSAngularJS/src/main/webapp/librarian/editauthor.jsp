<!DOCTYPE html>
<h3>Hello Admin! Edit Author details</h3>
	<form action="editAuthor" method="post">
		Edit Author Name:
		<input type="text" name="authorName" value=''>
		<input type="hidden" name="authorId" value=>
		<button type="submit">Edit Author</button>
	</form>
<script>
$(document).ready(function()
		{
		    $('.modal').on('hidden.bs.modal', function(e)
		    { 
		        $(this).removeData();
		    }) ;
		});
</script>