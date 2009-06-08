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

  def find
    content = Content.find params[:id]
    tool = 'doc'
    group = content.container.owner
    folder = content.container.id
    redirect_to :controller => 'contents', :action => 'show',
      :tool => tool, :group => group.shortName, :folder => folder.id, :content => content.id
  rescue ActiveRecord::RecordNotFound
    redirect_to :action => 'index'
  end
  
  def list
    @mclass = MODELS[params[:model].to_sym]
    @models = @mclass.find :all
  end
end
