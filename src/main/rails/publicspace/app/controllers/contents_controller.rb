class ContentsController < ApplicationController
  
  def show
    @group = Group.find_by_shortName!(params[:group])
    @tool = params[:tool]
    @folder = params[:folder]
    @content = params[:content]

    template(:basic, :docs,  { "content" => Content.find(params[:content]) } )
  end
end
