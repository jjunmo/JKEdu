import { useState } from "react";
import { Bar } from "react-chartjs-2";
import { Chart, registerables } from "chart.js";

import "App.css";

import styled from "styled-components";

const Graph = styled.div`
  width: 450px;
  height: 400px;

  @media screen and (max-width: 768px) {
    width: 90%;
    height: 300px;
    margin: 0 auto;
  }
`;

const Result4Graph = (prop) => {
  Chart.register(...registerables);

  // 나중에 결과를 받아와서 사용
  const [result, setResult] = useState(prop.result);

  // @explain 그래프 y축을 영어로 변경하기 위함
  const [yLabels, setYLabels] = useState({
    1: "Pre-A1",
    2: "A1",
    3: "A1+",
    4: "A2",
    5: "A2+",
    6: "B1",
    7: "B1+",
    8: "B2",
    9: "B2+",
    10: "C1",
  });

  const bardata = {
    labels: ["Speaking", "Reading", "Listening", "Grammar", "Writing"],
    datasets: [
      {
        label: "Your Level",
        backgroundColor: "#BAD7DF",
        data: [5, 2, 1, 9, 6],
        borderColor: "white",
        borderWidth: 1,
        fill: true,
        mim: 1,
        max: 10,
      },
    ],
  };

  return (
    <Graph>
      <Bar
        type="bar"
        data={bardata}
        options={{
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              ticks: {
                callback: function (value, index, ticks) {
                  return yLabels[value];
                },
              },
              suggestedMin: 1,
              suggestedMax: 10,
            },
          },
        }}
      />
    </Graph>
  );
};

export default Result4Graph;
