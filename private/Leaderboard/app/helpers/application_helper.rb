# Methods added to this helper will be available to all templates in the application.
module ApplicationHelper
  
  def add_member_link(name)
    link_to_function name do |page|
      page.insert_html :bottom, :members, :partial => 'member', :object => Member.new
    end
  end
  
  def display_standard_flashes(message = 'There were some problems with your submission:')
    if flash[:notice]
      flash_to_display, level = flash[:notice], 'notice'
    elsif flash[:warning]
      flash_to_display, level = flash[:warning], 'warning'
    elsif flash[:error]
      level = 'error'
      if flash[:error].instance_of? ActiveRecord::Errors
        flash_to_display = message
        flash_to_display << activerecord_error_list(flash[:error])
      else
        flash_to_display = flash[:error]
      end
    else
      return
    end
    content_tag 'div', flash_to_display, :class => "flash #{level}"
  end

  def activerecord_error_list(errors)
    error_list = '<ul class="error_list">'
    error_list << errors.collect do |e, m|
      "<li>#{e.humanize unless e == "base"} #{m}</li>"
    end.to_s << '</ul>'
    error_list
  end
  
  def external_link_to(name, options = {}, html_options = {}, window_name='new_window_name', height=350, width=450,*parameters_for_method_reference)
    link_to(name, options, 
      html_options.merge({:popup => [window_name, "toolbar=no,resizable=yes,scrollbars=yes,height=#{height},width=#{width}"]}),
      parameters_for_method_reference)
  end
  
  def popup_form_for(object_name, *args, &proc)
    options = args.last.is_a?(Hash) ? args.pop : {}
    options = options.merge({:complete => 'closeAndRefreshParent()'})
    concat(form_remote_tag(options), proc.binding)
    fields_for(object_name, *(args << options), &proc)
    concat('</form>', proc.binding)
  end
  
  def popup_button_to(name, options = {}, html_options = {})
    html_options = html_options.stringify_keys
    convert_boolean_attributes!(html_options, %w( disabled ))

    method_tag = ''
    if (method = html_options.delete('method')) && %w{put delete}.include?(method.to_s)
      method_tag = tag('input', :type => 'hidden', :name => '_method', :value => method.to_s)
    end
  
    form_method = method.to_s == 'get' ? 'get' : 'post'
  
    if confirm = html_options.delete("confirm")
      html_options["onclick"] = "return #{confirm_javascript_function(confirm)};"
    end
  
    url = options.is_a?(String) ? options : self.url_for(options)
    name ||= url
  
    html_options.merge!("type" => "submit", "value" => name)
    "<form method=\"#{form_method}\" action=\"#{escape_once url}\" class=\"button-to\" " + 
      "onsubmit=\"new Ajax.Request('#{escape_once url}', " + 
      "{asynchronous:true, evalScripts:true, onComplete:function(request){closeAndRefreshParent()}, " + 
      "parameters:Form.serialize(this)}); return false;\"><div>" + 
      method_tag + tag("input", html_options) + "</div></form>"
  end
  
  def score_criteria(event)
    if event.name == "Mountain Car"
      return :episodes
    elsif ["Helicopter", "Tetris", "RTS"].include?(event.name)
      return :total_reward
    end
  end
end
