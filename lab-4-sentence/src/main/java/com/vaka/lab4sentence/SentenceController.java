/*
 * Copyright (c) 2019 by Eyefreight BV (www.eyefreight.com). All rights reserved.
 *
 * This software is provided by the copyright holder and contributors "as is" and any express or implied warranties, including, but
 * not limited to, the implied warranties of merchantability and fitness for a particular purpose are disclaimed. In no event shall
 * Eyefreight BV or contributors be liable for any direct, indirect, incidental, special, exemplary, or consequential damages
 * (including, but not limited to, procurement of substitute goods or services; * loss of use, data, or profits; or business
 * interruption) however caused and on any theory of liability, whether in contract, strict liability, or tort (including
 * negligence or otherwise) arising in any way out of the use of this software, even if advised of the possibility of such damage.
 */
package com.vaka.lab4sentence;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author i.vakatsiienko.
 */
@RestController
public class SentenceController {

  @Autowired
  private DiscoveryClient client;

  @GetMapping("/sentence")
  public String getSentence() {
    return getWord("<module>lab-4-subject</module>") + " "
        + getWord("<module>lab-4-verb</module>") + " "
        + getWord("<module>lab-4-article</module>") + " "
        + getWord("<module>lab-4-adjective</module>") + " "
        + getWord("<module>lab-4-noun</module>") + "."
        ;
  }

  public String getWord(String service) {
    List<ServiceInstance> list = client.getInstances(service);
    if (list != null && list.size() > 0) {
      URI uri = list.get(0).getUri();
      if (uri != null) {
        return (new RestTemplate()).getForObject(uri, String.class);
      }
    }
    return null;
  }
}
