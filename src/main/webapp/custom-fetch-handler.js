// https://stackoverflow.com/questions/31041033/how-can-i-configure-polymers-platinum-sw-to-not-cache-one-url-path
// https://github.com/pillarjs/path-to-regexp
// https://forbeslindesay.github.io/express-route-tester/
var NoCacheFetchHandler = function(request, values, options) {
	// console.log('doNativeFetch request', request, values, options);
  return fetch(request);
}
