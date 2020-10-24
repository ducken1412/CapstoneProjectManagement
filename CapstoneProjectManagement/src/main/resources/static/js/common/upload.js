var totalsizeOfUploadFiles = 0;
function getFileSizeAndName(input)
{
    debugger;
    var select = $('#uploadFile');
    for(var i =0; i<input.files.length; i++)
    {
        var filesizeInBytes = input.files[i].size;
        var filesizeInMB = (filesizeInBytes / (1024*1024)).toFixed(2);
        var filename = input.files[i].name;
        //alert("File name is : "+filename+" || size : "+filesizeInMB+" MB || size : "+filesizeInBytes+" Bytes");
            select.append($('<label id=file'+i+'>'+filename+'</label><br>'));
        totalsizeOfUploadFiles += parseFloat(filesizeInMB);

    }
}

