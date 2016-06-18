// TODO Add this to gulp
var parsed = require('./manifest.json');
if (process.argv.length !== 3) {
  console.log('Missing img icon source (512x512 recommended)');
} else {
  var exec = require('child_process').exec;
  for (var i = 0; i < parsed.icons.length; i++) {
    var icon = parsed.icons[i];
    var cmd = 'convert ' + process.argv[2] + ' -resize ' + icon.sizes + ' ' + icon.src;
    exec(cmd, function (error, stdout, stderr) {
      if (error !== null) {
        console.log(error);
      }
    });
  }
}
