var totalsizeOfUploadFiles = 0;
function getFileSizeandName(input)
{
    debugger;
    var select = $('#uploadTable');
    for(var i =0; i<input.files.length; i++)
    {
        var filesizeInBytes = input.files[i].size;
        var filesizeInMB = (filesizeInBytes / (1024*1024)).toFixed(2);
        var filename = input.files[i].name;
        //alert("File name is : "+filename+" || size : "+filesizeInMB+" MB || size : "+filesizeInBytes+" Bytes");
            select.append($('<tr id=tr'+i+'><td id=filetd'+i+'>'+filename+'</td><td id=filesizetd'+i+'>'+filesizeInMB+'</td></tr>'));
        totalsizeOfUploadFiles += parseFloat(filesizeInMB);
        $('#totalsize').text(totalsizeOfUploadFiles.toFixed(2)+" MB");
        if(i==0)
            $('#filecount').text("1file");
        else
        {
            var no = parseInt(i) + 1;
            $('#filecount').text(no+"files");
        }
    }
}

function CloseAndRefresh()
{
    opener.location.reload(true);
    self.close();
}