
function getFileSizeAndName(input)
{
    var select = $('#uploadFile');


    for(var i =0; i<input.files.length; i++)
    {
        var filesizeInBytes = input.files[i].size;
        var filesizeInMB = (filesizeInBytes / (1024*1024)).toFixed(2);
        var filename = input.files[i].name;
        //alert("File name is : "+filename+" || size : "+filesizeInMB+" MB || size : "+filesizeInBytes+" Bytes");
            select.append($('<label >'+filename+'</label><br>'));
    }


}


function CloseAndRefresh()
{
    opener.location.reload(true);
    self.close();
}
