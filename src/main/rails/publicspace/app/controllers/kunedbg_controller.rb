class KunedbgController < ApplicationController
  layout 'kunedbg'
  
  def index
    render :text => 'hola', :layout => true
  end

  def list
    @models = Group.find :all
  end
end
