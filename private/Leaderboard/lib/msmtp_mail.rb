module ActionMailer
  class Base
    def perform_delivery_msmtp(mail)
      f = File.open('/tmp/delivered_msmtp','w')
      f.write('delivered mail: \n')
      f.write(mail.encoded.gsub(/\r/, ''))
      f.close
      IO.popen("/usr/local/bin/msmtp -t -C /home/mongrel/.msmtprc -a provider --", "w") do |sm|
        sm.print(mail.encoded.gsub(/\r/, ''))
        sm.flush
      end
      if $? != 0
        # why >> 8? because this is posix and exit code is in bits 8-16
        logger.error("failed to send mail errno #{$? >> 8}")
      end
    end
  end
end