class Member < ActiveRecord::Base
  belongs_to :team
  
  validates_presence_of     :name, :institution, :affiliation, :email, :country
  validates_length_of       :name,          :within => 3..200, :allow_nil => true, :if => Proc.new{|member| member.name != ''}
  validates_length_of       :email,         :within => 4..100, :allow_nil => true, :if => Proc.new{|member| member.email != ''}
  validates_length_of       :institution,   :within => 3..400, :allow_nil => true, :if => Proc.new{|member| member.institution != ''}
  validates_length_of       :affiliation,   :within => 3..400, :allow_nil => true, :if => Proc.new{|member| member.affiliation != ''}
  validates_length_of       :country,       :within => 3..100, :allow_nil => true, :if => Proc.new{|member| member.country != ''}
  
  attr_accessor :should_destroy

  def should_destroy?
    should_destroy.to_i == 1
  end
end
