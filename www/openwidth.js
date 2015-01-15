var exec = require('cordova/exec');



var openWith = {

	open:function(jobid,type,callback){
        var job = Settings.GetJob(jobid);
        openPDF.openWithJob(job,type,callback);
    },
    openWithJob:function(job,type,callback){
        cordova.getBasePath(function(path){
            var pdfPath = path + job.id + '/' + job.syncFrom + '/' + job.indexFile;
//            pdfPath = "http://www.tricedesigns.com/temp/drm.pdf";
            
            var fromTop = document.getElementById('JobMenu_cell_' + job.id).getBoundingClientRect().top;
            
            switch (app.device){
                case 'iOS':
                    openPDF.openIOS(pdfPath,type,fromTop,callback);
                    break;
                case 'Android':
                    openPDF.openAndroid(pdfPath,callback);
                    break;
                default:
                    alert('external file opening is not supportet on ' + app.device);
            }
        });
    },
    openAndroid:function(pdfPath,callback){
        
        console.log('opening ex file: ' + pdfPath);

        function succes(m){
        	console.log('succes');
            testCallback(m);
        };
        function error(m){
			console.log('error');
            testCallback(m);
        };
        exec(success, error, "openwidth", "open", [pdfPath]);
        
    },
    openIOS:function(pdfPath,type,fromTop,callback){
        var UTI = 'public.text';
        switch (type){
            case 'PDF':
                UTI = 'com.adobe.pdf';
                break;
        }
        console.log(UTI);
        
        function errorcallback(err){};
        
        var ret = exec(callback, errorcallback, "openwidth", "open", [pdfPath , UTI , fromTop]);
        //var ret = cordova.exec(callback, errorcallback, "ExternalFileUtil", "openWith", [pdfPath , UTI , fromTop]);
        console.log(ret);
        
    }

};

exports.open = openWith;

/*
exports.open = function(arg0, success, error) {
    exec(success, error, "openwidth", "open", [arg0]);
};
*/