ActionController::Routing::Routes.draw do |map|
  map.root :controller => 'documents'
  map.connect '/public/:controller/:action/:id'
  map.connect '/public/:controller/:action/:id.:format'
end
