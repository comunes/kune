class KunedbgController < ApplicationController
  layout 'kunedbg'

  helper_method :model_types

  MODELS = {:groups => Group, :content => Content, :revisions => Revision, :users => Kuser }

  def index
    render :text => '', :layout => true
  end

  def model_types
    MODELS.keys
  end
  
  def list
    @mclass = MODELS[params[:model].to_sym]
    @models = @mclass.find :all
  end
end
