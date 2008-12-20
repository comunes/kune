class ContentsController < ApplicationController

  def show
    @group = Group.find_by_shortName!(params[:group])
    @tool = params[:tool]
    @folder = params[:folder]
    @content = params[:content]
  end

end
