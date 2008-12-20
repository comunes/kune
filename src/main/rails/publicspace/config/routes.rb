ActionController::Routing::Routes.draw do |map|
  map.root :controller => 'kunedbg'
  map.connect 'public/:group/:tool/:folder/:content', :controller => 'contents', :action => 'show'
  map.connect '/public/debug/:action/:id', :controller => 'kunedbg'
  #map.connect '/public/:controller/:action/:id.:format'
end
